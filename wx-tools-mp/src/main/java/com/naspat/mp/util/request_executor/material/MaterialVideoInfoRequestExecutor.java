package com.naspat.mp.util.request_executor.material;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.mp.bean.material.WxMpMaterialVideoInfoResult;

import java.io.IOException;

public abstract class MaterialVideoInfoRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialVideoInfoResult, String> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialVideoInfoRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<WxMpMaterialVideoInfoResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
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
