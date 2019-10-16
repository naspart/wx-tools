package com.naspat.common.util.http;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

public interface RequestHttp {
    /**
     * 返回httpClient.
     *
     * @return 返回httpClient
     */
    CloseableHttpClient getRequestHttpClient();

    /**
     * 返回httpProxy.
     *
     * @return 返回httpProxy
     */
    HttpHost getRequestHttpProxy();
}
