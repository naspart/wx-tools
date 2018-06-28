package com.rolbel.ma.api.impl;

import com.rolbel.ma.api.WxMaQrcodeService;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.bean.WxMaCodeLineColor;
import com.rolbel.ma.bean.WxMaQrcode;
import com.rolbel.ma.bean.WxMaWxcode;
import com.rolbel.ma.bean.WxMaWxcodeLimit;
import com.rolbel.ma.util.http.QrCodeRequestExecutor;
import com.rolbel.common.error.WxErrorException;

import java.io.File;

public class WxMaQrcodeServiceImpl implements WxMaQrcodeService {
    private WxMaService wxMaService;

    public WxMaQrcodeServiceImpl(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public File createQrcode(String path, int width) throws WxErrorException {
        return this.wxMaService.execute(new QrCodeRequestExecutor(this.wxMaService.getRequestHttp()), CREATE_QRCODE_URL, new WxMaQrcode(path, width));
    }

    @Override
    public File createQrcode(String path) throws WxErrorException {
        return this.createQrcode(path, 430);
    }

    @Override
    @SuppressWarnings("unchecked")
    public File createWxCode(String path, int width, boolean autoColor, WxMaCodeLineColor lineColor) throws WxErrorException {
        WxMaWxcode wxMaWxcode = new WxMaWxcode();
        wxMaWxcode.setPath(path);
        wxMaWxcode.setWidth(width);
        wxMaWxcode.setAutoColor(autoColor);
        wxMaWxcode.setLineColor(lineColor);
        return this.wxMaService.execute(new QrCodeRequestExecutor(this.wxMaService.getRequestHttp()), GET_WXACODE_URL, wxMaWxcode);
    }

    @Override
    public File createWxCode(String path, int width) throws WxErrorException {
        return this.createWxCode(path, width, true, null);
    }

    @Override
    public File createWxCode(String path) throws WxErrorException {
        return this.createWxCode(path, 430, true, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public File createWxCodeLimit(String scene, String page, int width, boolean autoColor, WxMaCodeLineColor lineColor)
            throws WxErrorException {
        WxMaWxcodeLimit wxMaWxcodeLimit = new WxMaWxcodeLimit();
        wxMaWxcodeLimit.setScene(scene);
        wxMaWxcodeLimit.setPage(page);
        wxMaWxcodeLimit.setWidth(width);
        wxMaWxcodeLimit.setAutoColor(autoColor);
        wxMaWxcodeLimit.setLineColor(lineColor);

        return this.wxMaService.execute(new QrCodeRequestExecutor(this.wxMaService.getRequestHttp()), GET_WXACODE_UNLIMIT_URL, wxMaWxcodeLimit);
    }

    @Override
    public File createWxCodeLimit(String scene, String page) throws WxErrorException {
        return this.createWxCodeLimit(scene, page, 430, true, null);
    }

}
