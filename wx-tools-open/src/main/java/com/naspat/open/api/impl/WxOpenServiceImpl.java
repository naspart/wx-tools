package com.naspat.open.api.impl;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.SimpleGetRequestExecutor;
import com.naspat.common.util.http.SimplePostRequestExecutor;
import com.naspat.common.util.http.DefaultHttpClientBuilder;
import com.naspat.open.config.WxOpenConfig;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

public class WxOpenServiceImpl extends WxOpenServiceAbstractImpl {
    private CloseableHttpClient httpClient = DefaultHttpClientBuilder.get().build();
    private HttpHost httpProxy = null;

    @Override
    public void initHttp() {
        WxOpenConfig wxOpenConfig = this.getWxOpenConfig();
        if (wxOpenConfig.getHttpProxyHost() != null && wxOpenConfig.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(wxOpenConfig.getHttpProxyHost(), wxOpenConfig.getHttpProxyPort());
        }

        this.httpClient = DefaultHttpClientBuilder.get()
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
    public String get(String url, String queryParam) throws WxErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws WxErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }
}
