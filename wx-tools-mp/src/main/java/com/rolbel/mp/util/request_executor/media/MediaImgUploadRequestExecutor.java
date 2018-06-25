package com.rolbel.mp.util.request_executor.media;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.material.WxMediaImgUploadResult;

import java.io.File;

/**
 * @author miller
 */
public abstract class MediaImgUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaImgUploadResult, File> {
    protected RequestHttp<H, P> requestHttp;

    public MediaImgUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

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
