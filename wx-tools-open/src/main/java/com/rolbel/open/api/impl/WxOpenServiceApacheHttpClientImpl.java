package com.rolbel.open.api.impl;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.SimpleGetRequestExecutor;
import com.rolbel.common.util.http.SimplePostRequestExecutor;
import com.rolbel.common.util.http.apache.DefaultApacheHttpClientBuilder;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * apache-http方式实现
 *
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenServiceApacheHttpClientImpl extends WxOpenServiceAbstractImpl<CloseableHttpClient, HttpHost> {
    private CloseableHttpClient httpClient = DefaultApacheHttpClientBuilder.get().build();
    private HttpHost httpProxy = null;

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
