package com.rolbel.common.util.http;

import com.rolbel.common.bean.result.WxMediaUploadResult;

import java.io.File;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 *
 * @author Daniel Qian
 */
public abstract class MediaUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaUploadResult, File> {
    protected RequestHttp<H, P> requestHttp;

    public MediaUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<WxMediaUploadResult, File> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMediaUploadRequestExecutor(requestHttp);
            default:
                return null;
        }
    }
}
