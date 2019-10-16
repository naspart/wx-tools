package com.naspat.common.util.http;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.apache.ApacheHttpClientSimpleGetRequestExecutor;

import java.io.IOException;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 */
public abstract class SimpleGetRequestExecutor<H, P> implements RequestExecutor<String, String> {
    protected RequestHttp<H, P> requestHttp;

    public SimpleGetRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheHttpClientSimpleGetRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }
}
