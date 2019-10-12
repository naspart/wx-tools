package com.naspat.mp.api.impl;

import com.naspat.common.WxType;
import com.naspat.common.bean.WxAccessToken;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.HttpType;
import com.naspat.mp.config.WxMpConfig;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;

import java.util.concurrent.locks.Lock;

import static com.naspat.mp.enums.WxMpApiUrl.Other.GET_ACCESS_TOKEN_URL;

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
        WxMpConfig wxMpConfig = this.getWxMpConfig();

        if (wxMpConfig.getHttpProxyHost() != null && wxMpConfig.getHttpProxyPort() > 0) {
            httpProxy = new ProxyInfo(ProxyInfo.ProxyType.HTTP, wxMpConfig.getHttpProxyHost(), wxMpConfig.getHttpProxyPort(), wxMpConfig.getHttpProxyUsername(), wxMpConfig.getHttpProxyPassword());
        }

        httpClient = new SocketHttpConnectionProvider();
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        final WxMpConfig config = this.getWxMpConfig();
        if (!config.isAccessTokenExpired() && !forceRefresh) {
            return config.getAccessToken();
        }

        Lock lock = config.getAccessTokenLock();
        lock.lock();
        try {
            String url = String.format(GET_ACCESS_TOKEN_URL.getUrl(config), config.getAppId(), config.getSecret());

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
                throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            config.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());

            return config.getAccessToken();
        } finally {
            lock.unlock();
        }
    }
}
