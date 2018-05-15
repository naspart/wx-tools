package com.rolbel.mp.util.http;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;

public abstract class MaterialDeleteRequestExecutor<H, P> implements RequestExecutor<Boolean, String> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialDeleteRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMaterialDeleteRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
