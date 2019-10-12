package com.naspat.mp.api.impl;

import com.naspat.common.WxType;
import com.naspat.common.bean.WxAccessToken;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.HttpType;
import com.naspat.common.util.http.okhttp.OkHttpProxyInfo;
import com.naspat.mp.config.WxMpConfig;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import static com.naspat.mp.enums.WxMpApiUrl.Other.GET_ACCESS_TOKEN_URL;

/**
 * okhttp实现
 */
public class WxMpServiceOkHttpImpl extends WxMpServiceBaseImpl<OkHttpClient, OkHttpProxyInfo> {
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
    public void initHttp() {
        this.logger.debug("WxMpServiceOkHttpImpl initHttp");
        WxMpConfig wxMpConfig = this.getWxMpConfig();
        //设置代理
        if (wxMpConfig.getHttpProxyHost() != null && wxMpConfig.getHttpProxyPort() > 0) {
            httpProxy = OkHttpProxyInfo.httpProxy(wxMpConfig.getHttpProxyHost(),
                    wxMpConfig.getHttpProxyPort(),
                    wxMpConfig.getHttpProxyUsername(),
                    wxMpConfig.getHttpProxyPassword());
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

            Request request = new Request.Builder().url(url).get().build();
            Response response = getRequestHttpClient().newCall(request).execute();
            String resultContent = response.body().string();
            WxError error = WxError.fromJson(resultContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            config.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());

            return config.getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
