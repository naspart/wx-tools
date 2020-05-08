package com.naspat.mp.api.impl;

import com.naspat.common.util.http.DefaultHttpClientBuilder;
import com.naspat.common.util.http.HttpClientBuilder;
import com.naspat.mp.config.WxMpConfig;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * <pre>
 * 默认接口实现类，使用apache httpclient实现
 * </pre>
 */
public class WxMpServiceImpl extends WxMpServiceBaseImpl {
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
    public void initHttp() {
        HttpClientBuilder httpClientBuilder = DefaultHttpClientBuilder.get();

        WxMpConfig wxMpConfig = this.getWxMpConfig();
        httpClientBuilder.httpProxyHost(wxMpConfig.getHttpProxyHost())
                .httpProxyPort(wxMpConfig.getHttpProxyPort())
                .httpProxyUsername(wxMpConfig.getHttpProxyUsername())
                .httpProxyPassword(wxMpConfig.getHttpProxyPassword());

        if (wxMpConfig.getHttpProxyHost() != null && wxMpConfig.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(wxMpConfig.getHttpProxyHost(), wxMpConfig.getHttpProxyPort());
        }

        this.httpClient = httpClientBuilder.build();
    }
}
