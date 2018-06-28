package com.rolbel.common.util.http;

import com.rolbel.common.util.http.apache.ApacheMediaDownloadRequestExecutor;
import com.rolbel.common.util.http.jodd.JoddHttpMediaDownloadRequestExecutor;
import com.rolbel.common.util.http.okhttp.OkHttpMediaDownloadRequestExecutor;

import java.io.File;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 */
public abstract class BaseMediaDownloadRequestExecutor<H, P> implements RequestExecutor<File, String> {
    protected RequestHttp<H, P> requestHttp;
    protected File tmpDirFile;

    public BaseMediaDownloadRequestExecutor(RequestHttp<H, P> requestHttp, File tmpDirFile) {
        this.requestHttp = requestHttp;
        this.tmpDirFile = tmpDirFile;
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
            case JODD_HTTP:
                return new JoddHttpMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
            case OK_HTTP:
                return new OkHttpMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
            default:
                return null;
        }
    }
}
