package com.naspat.mp.util.request_executor.material;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.mp.bean.material.WxMpMaterialNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class MaterialNewsInfoRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialNews, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected RequestHttp<H, P> requestHttp;

    public MaterialNewsInfoRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<WxMpMaterialNews> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<WxMpMaterialNews, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialNewsInfoApacheHttpRequestExecutor(requestHttp);
            default:
                //TODO 需要优化抛出异常
                return null;
        }
    }
}
