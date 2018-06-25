package com.rolbel.mp.util.request_executor.qrcode;

import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.result.WxMpQrCodeTicket;

import java.io.File;

/**
 * 获得QrCode图片 请求执行器
 */
public abstract class QrCodeRequestExecutor<H, P> implements RequestExecutor<File, WxMpQrCodeTicket> {
    protected RequestHttp<H, P> requestHttp;

    public QrCodeRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

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
