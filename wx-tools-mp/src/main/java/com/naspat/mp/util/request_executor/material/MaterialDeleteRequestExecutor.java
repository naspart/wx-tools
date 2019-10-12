package com.naspat.mp.util.request_executor.material;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class MaterialDeleteRequestExecutor<H, P> implements RequestExecutor<Boolean, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected RequestHttp<H, P> requestHttp;

    public MaterialDeleteRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<Boolean> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialDeleteApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialDeleteJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialDeleteOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
