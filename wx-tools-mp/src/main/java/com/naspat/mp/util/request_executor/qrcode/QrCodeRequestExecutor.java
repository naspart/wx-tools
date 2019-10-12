package com.naspat.mp.util.request_executor.qrcode;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.mp.bean.WxMpQrCodeTicket;

import java.io.File;
import java.io.IOException;

/**
 * 获得QrCode图片 请求执行器
 */
public abstract class QrCodeRequestExecutor<H, P> implements RequestExecutor<File, WxMpQrCodeTicket> {
    protected RequestHttp<H, P> requestHttp;

    public QrCodeRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, WxMpQrCodeTicket data, ResponseHandler<File> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<File, WxMpQrCodeTicket> create(RequestHttp requestHttp) throws WxErrorException {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new QrCodeApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new QrCodeJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new QrCodeOkhttpRequestExecutor(requestHttp);
            default:
                throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("不支持的http框架").build());
        }
    }
}
