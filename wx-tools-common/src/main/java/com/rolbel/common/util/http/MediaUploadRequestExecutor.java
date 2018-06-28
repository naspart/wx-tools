package com.rolbel.common.util.http;

import com.rolbel.common.bean.result.WxMediaUploadResult;
import com.rolbel.common.util.http.apache.ApacheMediaUploadRequestExecutor;
import com.rolbel.common.util.http.jodd.JoddHttpMediaUploadRequestExecutor;
import com.rolbel.common.util.http.okhttp.OkHttpMediaUploadRequestExecutor;

import java.io.File;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 */
public abstract class MediaUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaUploadResult, File> {
    protected RequestHttp<H, P> requestHttp;

    public MediaUploadRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<WxMediaUploadResult, File> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMediaUploadRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new JoddHttpMediaUploadRequestExecutor(requestHttp);
            case OK_HTTP:
                return new OkHttpMediaUploadRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }
}
