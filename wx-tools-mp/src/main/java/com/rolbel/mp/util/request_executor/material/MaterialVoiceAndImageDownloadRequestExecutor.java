package com.rolbel.mp.util.request_executor.material;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class MaterialVoiceAndImageDownloadRequestExecutor<H, P> implements RequestExecutor<InputStream, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected RequestHttp<H, P> requestHttp;
    protected File tmpDirFile;

    public MaterialVoiceAndImageDownloadRequestExecutor(RequestHttp<H, P> requestHttp, File tmpDirFile) {
        this.requestHttp = requestHttp;
        this.tmpDirFile = tmpDirFile;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<InputStream> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<InputStream, String> create(RequestHttp requestHttp, File tmpDirFile) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialVoiceAndImageDownloadApacheHttpRequestExecutor(requestHttp, tmpDirFile);
            case JODD_HTTP:
                return new MaterialVoiceAndImageDownloadJoddHttpRequestExecutor(requestHttp, tmpDirFile);
            case OK_HTTP:
                return new MaterialVoiceAndImageDownloadOkhttpRequestExecutor(requestHttp, tmpDirFile);
            default:
                return null;
        }
    }
}
