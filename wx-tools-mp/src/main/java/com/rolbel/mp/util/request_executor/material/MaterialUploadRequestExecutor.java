package com.rolbel.mp.util.request_executor.material;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.material.WxMpMaterial;
import com.rolbel.mp.bean.material.WxMpMaterialUploadResult;

/**
 * @author codepiano
 */
public abstract class MaterialUploadRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialUploadApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialUploadJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialUploadOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }
}
