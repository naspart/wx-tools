package com.rolbel.mp.util.request_executor.voice;

import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;

import java.io.File;

/**
 * <pre>
 *  Created by BinaryWang on 2018/6/9.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public abstract class VoiceUploadRequestExecutor<H, P> implements RequestExecutor<Boolean, File> {
    protected RequestHttp<H, P> requestHttp;

    public VoiceUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<Boolean, File> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new VoiceUploadApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
            case OK_HTTP:
            default:
                return null;
        }
    }
}
