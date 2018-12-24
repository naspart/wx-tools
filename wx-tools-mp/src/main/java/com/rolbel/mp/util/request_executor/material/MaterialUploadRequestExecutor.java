package com.rolbel.mp.util.request_executor.material;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.ResponseHandler;
import com.rolbel.mp.bean.material.WxMpMaterial;
import com.rolbel.mp.bean.material.WxMpMaterialUploadResult;

import java.io.IOException;

public abstract class MaterialUploadRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialUploadRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, WxMpMaterial data, ResponseHandler<WxMpMaterialUploadResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
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
