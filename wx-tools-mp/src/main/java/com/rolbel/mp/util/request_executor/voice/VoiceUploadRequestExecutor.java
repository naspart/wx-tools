package com.rolbel.mp.util.request_executor.voice;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.ResponseHandler;

import java.io.File;
import java.io.IOException;

public abstract class VoiceUploadRequestExecutor<H, P> implements RequestExecutor<Boolean, File> {
    protected RequestHttp<H, P> requestHttp;

    public VoiceUploadRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, File data, ResponseHandler<Boolean> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
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
