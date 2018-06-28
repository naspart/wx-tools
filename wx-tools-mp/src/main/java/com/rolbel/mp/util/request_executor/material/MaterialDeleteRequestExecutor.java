package com.rolbel.mp.util.request_executor.material;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MaterialDeleteRequestExecutor<H, P> implements RequestExecutor<Boolean, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected RequestHttp<H, P> requestHttp;

    public MaterialDeleteRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialDeleteApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialDeleteJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialDeleteOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
