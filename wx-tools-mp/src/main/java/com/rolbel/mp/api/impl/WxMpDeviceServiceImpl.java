package com.rolbel.mp.api.impl;

import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.api.WxMpDeviceService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.device.*;
import com.rolbel.mp.bean.device.result.*;

public class WxMpDeviceServiceImpl implements WxMpDeviceService {
    private static final String API_URL_PREFIX = "https://api.weixin.qq.com/device";

    private WxMpService wxMpService;

    public WxMpDeviceServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public TransMsgResp transMsg(WxDeviceMsg msg) throws WxErrorException {
        String responseContent = this.wxMpService.post(TRANS_MSG_URL, msg.toJson());

        return TransMsgResp.fromJson(responseContent);
    }

    @Override
    public WxDeviceQrCodeResult getQrCode(String productId) throws WxErrorException {
        String response = this.wxMpService.get(GET_QRCODE_URL, "product_id=" + productId);

        return WxDeviceQrCodeResult.fromJson(response);
    }

    @Override
    public WxDeviceAuthorizeResult authorize(WxDeviceAuthorize wxDeviceAuthorize) throws WxErrorException {
        String response = this.wxMpService.post(AUTHORIZE_DEVICE_URL, wxDeviceAuthorize.toJson());

        return WxDeviceAuthorizeResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult bind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(BIND_DEVICE_URL, wxDeviceBind.toJson());

        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult compelBind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(COMPEL_BIND_DEVICE_URL, wxDeviceBind.toJson());

        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult unbind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(UNBIND_DEVICE_URL, wxDeviceBind.toJson());

        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult compelUnbind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(COMPEL_UNBIND_DEVICE_URL, wxDeviceBind.toJson());

        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceOpenIdResult getOpenId(String deviceType, String deviceId) throws WxErrorException {
        String url = API_URL_PREFIX + "/get_openid";
        String response = this.wxMpService.get(url, "device_type=" + deviceType + "&device_id=" + deviceId);
        return WxDeviceOpenIdResult.fromJson(response);
    }

    @Override
    public WxDeviceBindDeviceResult getBindDevice(String openId) throws WxErrorException {
        String url = API_URL_PREFIX + "/get_bind_device";
        String response = this.wxMpService.get(url, "openid=" + openId);
        return WxDeviceBindDeviceResult.fromJson(response);
    }
}

