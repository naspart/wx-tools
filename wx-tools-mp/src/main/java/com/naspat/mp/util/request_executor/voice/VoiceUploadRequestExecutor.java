package com.naspat.mp.util.request_executor.voice;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;

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
            default:
                return null;
        }
    }
}
