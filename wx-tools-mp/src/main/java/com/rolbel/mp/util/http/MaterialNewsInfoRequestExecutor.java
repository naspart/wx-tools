package com.rolbel.mp.util.http;


import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.material.WxMpMaterialNews;

import static com.rolbel.common.util.http.HttpType.APACHE_HTTP;

public abstract class MaterialNewsInfoRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialNews, String> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialNewsInfoRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<WxMpMaterialNews, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMaterialNewsInfoRequestExecutor(requestHttp);
            default:
                //TODO 需要优化抛出异常
                return null;
        }
    }
}
