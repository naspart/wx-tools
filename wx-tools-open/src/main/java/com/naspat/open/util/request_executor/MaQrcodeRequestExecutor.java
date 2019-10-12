package com.naspat.open.util.request_executor;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.open.bean.ma.WxMaQrcodeParam;

import java.io.File;
import java.io.IOException;

/**
 * 获得小程序体验QrCode图片 请求执行器.
 */
public abstract class MaQrcodeRequestExecutor<H, P> implements RequestExecutor<File, WxMaQrcodeParam> {
    protected RequestHttp<H, P> requestHttp;

    public MaQrcodeRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, WxMaQrcodeParam data, ResponseHandler<File> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<File, WxMaQrcodeParam> create(RequestHttp requestHttp) throws WxErrorException {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaQrcodeApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaQrcodeJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaQrcodeOkhttpRequestExecutor(requestHttp);
            default:
                throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("不支持的http框架").build());
        }
    }
}
