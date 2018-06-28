package com.rolbel.common.util.http;

public interface RequestHttp<H, P> {

    /**
     * 返回httpClient
     */
    H getRequestHttpClient();

    /**
     * 返回httpProxy
     */
    P getRequestHttpProxy();

    HttpType getRequestType();
}
