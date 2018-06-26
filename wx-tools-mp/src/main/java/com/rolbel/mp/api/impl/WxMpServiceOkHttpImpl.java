package com.rolbel.mp.api.impl;

import com.rolbel.common.WxType;
import com.rolbel.common.bean.WxAccessToken;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.okhttp.OkHttpProxyInfo;
import com.rolbel.mp.api.WxMpConfigStorage;
import com.rolbel.mp.api.WxMpService;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * okhttp实现
 */
public class WxMpServiceOkHttpImpl extends WxMpServiceBaseImpl<OkHttpClient, OkHttpProxyInfo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private OkHttpClient httpClient;
    private OkHttpProxyInfo httpProxy;

    @Override
    public OkHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public OkHttpProxyInfo getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.OK_HTTP;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        this.logger.debug("WxMpServiceOkHttpImpl is running");
        Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
        try {
            lock.lock();

            if (this.getWxMpConfigStorage().isAccessTokenExpired() || forceRefresh) {
                String url = String.format(WxMpService.GET_ACCESS_TOKEN_URL,
                        this.getWxMpConfigStorage().getAppId(), this.getWxMpConfigStorage().getSecret());

                Request request = new Request.Builder().url(url).get().build();
                Response response = getRequestHttpClient().newCall(request).execute();
                String resultContent = response.body().string();
                WxError error = WxError.fromJson(resultContent, WxType.MP);
                if (error.getErrorCode() != 0) {
                    throw new WxErrorException(error);
                }
                WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                this.getWxMpConfigStorage().updateAccessToken(accessToken.getAccessToken(),
                        accessToken.getExpiresIn());
            }
        } catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
        return this.getWxMpConfigStorage().getAccessToken();
    }

    @Override
    public void initHttp() {
        this.logger.debug("WxMpServiceOkHttpImpl initHttp");
        WxMpConfigStorage wxMpConfigStorage = this.getWxMpConfigStorage();
        //设置代理
        if (wxMpConfigStorage.getHttpProxyHost() != null && wxMpConfigStorage.getHttpProxyPort() > 0) {
            httpProxy = OkHttpProxyInfo.httpProxy(wxMpConfigStorage.getHttpProxyHost(),
                    wxMpConfigStorage.getHttpProxyPort(),
                    wxMpConfigStorage.getHttpProxyUsername(),
                    wxMpConfigStorage.getHttpProxyPassword());
        }

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (httpProxy != null) {
            clientBuilder.proxy(getRequestHttpProxy().getProxy());

            //设置授权
            clientBuilder.authenticator((route, response) -> {
                String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            });
        }
        httpClient = clientBuilder.build();
    }

}
