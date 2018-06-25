package com.rolbel.mp.util.request_executor.material;


import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.material.WxMpMaterialVideoInfoResult;


public abstract class MaterialVideoInfoRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialVideoInfoResult, String> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialVideoInfoRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<WxMpMaterialVideoInfoResult, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialVideoInfoApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialVideoInfoJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialVideoInfoOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
