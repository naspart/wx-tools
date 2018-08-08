package com.rolbel.open.api.impl;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.SimpleGetRequestExecutor;
import com.rolbel.common.util.http.SimplePostRequestExecutor;
import com.rolbel.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.rolbel.open.api.WxOpenConfigStorage;
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
        WxOpenConfigStorage configStorage = this.getWxOpenConfigStorage();
        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort());
        }

        this.httpClient = DefaultApacheHttpClientBuilder.get()
                .httpProxyHost(configStorage.getHttpProxyHost())
                .httpProxyPort(configStorage.getHttpProxyPort())
                .httpProxyUsername(configStorage.getHttpProxyUsername())
                .httpProxyPassword(configStorage.getHttpProxyPassword()).build();
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
