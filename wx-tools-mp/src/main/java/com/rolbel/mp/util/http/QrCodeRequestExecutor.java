package com.rolbel.mp.util.http;

import com.rolbel.common.bean.result.WxError;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.result.WxMpQrCodeTicket;

import java.io.File;

/**
 * 获得QrCode图片 请求执行器
 *
 * @author chanjarster
 */
public abstract class QrCodeRequestExecutor<H, P> implements RequestExecutor<File, WxMpQrCodeTicket> {
    protected RequestHttp<H, P> requestHttp;

    public QrCodeRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    public static RequestExecutor<File, WxMpQrCodeTicket> create(RequestHttp requestHttp) throws WxErrorException {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheQrCodeRequestExecutor(requestHttp);
            default:
                throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("不支持的http框架").build());
        }
    }
}
