package com.naspat.ma.api.impl;

import com.google.gson.JsonParser;
import com.naspat.common.bean.WxJsapiSignature;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.RandomUtils;
import com.naspat.common.util.crypto.SHA1;
import com.naspat.ma.api.WxMaJsapiService;
import com.naspat.ma.api.WxMaService;

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
