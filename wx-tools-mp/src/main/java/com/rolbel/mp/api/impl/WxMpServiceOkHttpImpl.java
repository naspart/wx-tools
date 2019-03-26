package com.rolbel.mp.api.impl;

import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.okhttp.OkHttpProxyInfo;
import com.rolbel.mp.config.WxMpConfig;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;

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

}
