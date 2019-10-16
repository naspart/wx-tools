package com.naspat.common.util.http;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * httpclient build interface.
 */
public interface HttpClientBuilder {

    /**
     * 构建httpclient实例.
     *
     * @return new instance of CloseableHttpClient
     */
    CloseableHttpClient build();

    /**
     * 代理服务器地址.
     */
    HttpClientBuilder httpProxyHost(String httpProxyHost);

    /**
     * 代理服务器端口.
     */
    HttpClientBuilder httpProxyPort(int httpProxyPort);

    /**
     * 代理服务器用户名.
     */
    HttpClientBuilder httpProxyUsername(String httpProxyUsername);

    /**
     * 代理服务器密码.
     */
    HttpClientBuilder httpProxyPassword(String httpProxyPassword);

    /**
     * ssl连接socket工厂.
     */
    HttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory);
}
