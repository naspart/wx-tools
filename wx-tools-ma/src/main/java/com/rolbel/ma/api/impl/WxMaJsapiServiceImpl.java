package com.rolbel.ma.api.impl;

import com.google.gson.JsonParser;
import com.rolbel.common.bean.WxJsapiSignature;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.RandomUtils;
import com.rolbel.common.util.crypto.SHA1;
import com.rolbel.ma.api.WxMaJsapiService;
import com.rolbel.ma.api.WxMaService;

public class WxMaJsapiServiceImpl implements WxMaJsapiService {
    private static final JsonParser JSON_PARSER = new JsonParser();

    private WxMaService wxMaService;

    public WxMaJsapiServiceImpl(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    @Override
    public String getCardApiTicket() throws WxErrorException {
        return this.wxMaService.getWxMaConfig().getCardApiTicket();
    }

    @Override
    public String getJsapiTicket() throws WxErrorException {
        return this.wxMaService.getWxMaConfig().getJsapiTicket();
    }

    @Override
    public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String randomStr = RandomUtils.getRandomStr();
        String jsapiTicket = this.getJsapiTicket();
        String signature = SHA1.genWithAmple("jsapi_ticket=" + jsapiTicket,
                "noncestr=" + randomStr, "timestamp=" + timestamp, "url=" + url);

        return WxJsapiSignature
                .builder()
                .appId(this.wxMaService.getWxMaConfig().getAppId())
                .timestamp(timestamp)
                .nonceStr(randomStr)
                .url(url)
                .signature(signature)
                .build();
    }
}
