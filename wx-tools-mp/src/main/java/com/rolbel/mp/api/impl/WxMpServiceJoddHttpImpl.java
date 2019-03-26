package com.rolbel.mp.api.impl;

import com.rolbel.common.util.http.HttpType;
import com.rolbel.mp.config.WxMpConfig;
import jodd.http.HttpConnectionProvider;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;

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
}
