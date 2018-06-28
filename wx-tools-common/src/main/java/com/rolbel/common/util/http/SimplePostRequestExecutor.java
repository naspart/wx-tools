package com.rolbel.common.util.http;

import com.rolbel.common.util.http.apache.ApacheSimplePostRequestExecutor;
import com.rolbel.common.util.http.jodd.JoddHttpSimplePostRequestExecutor;
import com.rolbel.common.util.http.okhttp.OkHttpSimplePostRequestExecutor;

/**
 * 用装饰模式实现
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 */
public abstract class SimplePostRequestExecutor<H, P> implements RequestExecutor<String, String> {
    protected RequestHttp<H, P> requestHttp;

    public SimplePostRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheSimplePostRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new JoddHttpSimplePostRequestExecutor(requestHttp);
            case OK_HTTP:
                return new OkHttpSimplePostRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }
}
