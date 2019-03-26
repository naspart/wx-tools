package com.rolbel.mp.api.impl;

import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import com.rolbel.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.rolbel.mp.config.WxMpConfig;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

public class WxMpServiceHttpClientImpl extends WxMpServiceBaseImpl<CloseableHttpClient, HttpHost> {
    private CloseableHttpClient httpClient;
    private HttpHost httpProxy;

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.APACHE_HTTP;
    }

    @Override
    public void initHttp() {
        WxMpConfig wxMpConfig = this.getWxMpConfig();
        ApacheHttpClientBuilder apacheHttpClientBuilder = wxMpConfig.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }

        apacheHttpClientBuilder.httpProxyHost(wxMpConfig.getHttpProxyHost())
                .httpProxyPort(wxMpConfig.getHttpProxyPort())
                .httpProxyUsername(wxMpConfig.getHttpProxyUsername())
                .httpProxyPassword(wxMpConfig.getHttpProxyPassword());

        if (wxMpConfig.getHttpProxyHost() != null && wxMpConfig.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(wxMpConfig.getHttpProxyHost(), wxMpConfig.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();
    }
}
