package com.rolbel.mp.api.impl;

import com.rolbel.common.WxType;
import com.rolbel.common.bean.WxAccessToken;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.HttpType;
import com.rolbel.mp.api.WxMpConfigStorage;
import com.rolbel.mp.api.WxMpService;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;

import java.util.concurrent.locks.Lock;

/**
 * jodd-http方式实现
 */
public class WxMpServiceJoddHttpImpl extends WxMpServiceBaseImpl<HttpConnectionProvider, ProxyInfo> {
    private HttpConnectionProvider httpClient;
    private ProxyInfo httpProxy;

    @Override
    public HttpConnectionProvider getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public ProxyInfo getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.JODD_HTTP;
    }

    @Override
    public void initHttp() {
        WxMpConfigStorage configStorage = this.getWxMpConfigStorage();

        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            httpProxy = new ProxyInfo(ProxyInfo.ProxyType.HTTP, configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort(), configStorage.getHttpProxyUsername(), configStorage.getHttpProxyPassword());
        }

        httpClient = new SocketHttpConnectionProvider();
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
        try {
            lock.lock();

            if (this.getWxMpConfigStorage().isAccessTokenExpired() || forceRefresh) {
                String url = String.format(WxMpService.GET_ACCESS_TOKEN_URL,
                        this.getWxMpConfigStorage().getAppId(), this.getWxMpConfigStorage().getSecret());

                HttpRequest request = HttpRequest.get(url);

                if (this.getRequestHttpProxy() != null) {
                    SocketHttpConnectionProvider provider = new SocketHttpConnectionProvider();
                    provider.useProxy(getRequestHttpProxy());

                    request.withConnectionProvider(provider);
                }
                HttpResponse response = request.send();
                String resultContent = response.bodyText();
                WxError error = WxError.fromJson(resultContent, WxType.MP);
                if (error.getErrorCode() != 0) {
                    this.logger.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", url, null, error);
                    throw new WxErrorException(error);
                }
                WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                this.getWxMpConfigStorage().updateAccessToken(accessToken.getAccessToken(),
                        accessToken.getExpiresIn());
            }
        } finally {
            lock.unlock();
        }
        return this.getWxMpConfigStorage().getAccessToken();
    }
}
