package com.rolbel.mp.util.http;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.material.WxMpMaterial;
import com.rolbel.mp.bean.material.WxMpMaterialUploadResult;

public abstract class MaterialUploadRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMaterialUploadRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
