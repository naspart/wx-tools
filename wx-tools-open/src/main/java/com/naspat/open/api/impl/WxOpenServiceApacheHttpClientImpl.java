package com.naspat.open.api.impl;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.HttpType;
import com.naspat.common.util.http.SimpleGetRequestExecutor;
import com.naspat.common.util.http.SimplePostRequestExecutor;
import com.naspat.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.naspat.open.config.WxOpenConfig;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * apache-http方式实现
 */
public class WxOpenServiceApacheHttpClientImpl extends WxOpenServiceAbstractImpl<CloseableHttpClient, HttpHost> {
    private CloseableHttpClient httpClient = DefaultApacheHttpClientBuilder.get().build();
    private HttpHost httpProxy = null;

    @Override
    public void initHttp() {
        WxOpenConfig wxOpenConfig = this.getWxOpenConfig();
        if (wxOpenConfig.getHttpProxyHost() != null && wxOpenConfig.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(wxOpenConfig.getHttpProxyHost(), wxOpenConfig.getHttpProxyPort());
        }

        this.httpClient = DefaultApacheHttpClientBuilder.get()
                .httpProxyHost(wxOpenConfig.getHttpProxyHost())
                .httpProxyPort(wxOpenConfig.getHttpProxyPort())
                .httpProxyUsername(wxOpenConfig.getHttpProxyUsername())
                .httpProxyPassword(wxOpenConfig.getHttpProxyPassword()).build();
    }

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
    public String get(String url, String queryParam) throws WxErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws WxErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }
}
