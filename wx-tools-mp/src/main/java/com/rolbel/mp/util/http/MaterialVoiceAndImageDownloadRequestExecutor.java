package com.rolbel.mp.util.http;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;

import java.io.File;
import java.io.InputStream;

public abstract class MaterialVoiceAndImageDownloadRequestExecutor<H, P> implements RequestExecutor<InputStream, String> {
    protected RequestHttp<H, P> requestHttp;
    protected File tmpDirFile;

    public MaterialVoiceAndImageDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
        this.requestHttp = requestHttp;
        this.tmpDirFile = tmpDirFile;
    }

    public static RequestExecutor<InputStream, String> create(RequestHttp requestHttp, File tmpDirFile) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheMaterialVoiceAndImageDownloadRequestExecutor(requestHttp, tmpDirFile);
            default:
                return null;
        }
    }
}
