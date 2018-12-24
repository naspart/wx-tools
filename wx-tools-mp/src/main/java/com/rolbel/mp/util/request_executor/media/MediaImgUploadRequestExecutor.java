package com.rolbel.mp.util.request_executor.media;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.ResponseHandler;
import com.rolbel.mp.bean.material.WxMediaImgUploadResult;

import java.io.File;
import java.io.IOException;

public abstract class MediaImgUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaImgUploadResult, File> {
    protected RequestHttp<H, P> requestHttp;

    public MediaImgUploadRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, File data, ResponseHandler<WxMediaImgUploadResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<WxMediaImgUploadResult, File> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MediaImgUploadApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MediaImgUploadHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MediaImgUploadOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }
}
