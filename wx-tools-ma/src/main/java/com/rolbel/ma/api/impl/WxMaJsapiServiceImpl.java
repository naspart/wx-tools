package com.rolbel.ma.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rolbel.common.bean.WxJsapiSignature;
import com.rolbel.common.enums.TicketType;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.RandomUtils;
import com.rolbel.common.util.crypto.SHA1;
import com.rolbel.ma.api.WxMaJsapiService;
import com.rolbel.ma.api.WxMaService;

import java.util.concurrent.locks.Lock;

public class WxMaJsapiServiceImpl implements WxMaJsapiService {
    private static final JsonParser JSON_PARSER = new JsonParser();

    private WxMaService wxMaService;

    public WxMaJsapiServiceImpl(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    public String getCardApiTicket() throws WxErrorException {
        return getCardApiTicket(false);
    }

    public String getCardApiTicket(boolean forceRefresh) throws WxErrorException {
        final TicketType type = TicketType.WX_CARD;
        Lock lock = this.wxMaService.getWxMaConfig().getTicketLock(type);
        try {
            lock.lock();
            if (forceRefresh) {
                this.wxMaService.getWxMaConfig().expireTicket(type);
            }

            if (this.wxMaService.getWxMaConfig().isTicketExpired(type)) {
                String responseContent = this.wxMaService.get(GET_JSAPI_TICKET_URL + "?type=wx_card", null);
                JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
                JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
                String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
                int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
                this.wxMaService.getWxMaConfig().updateTicket(type, jsapiTicket, expiresInSeconds);
            }
        } finally {
            lock.unlock();
        }
        return this.wxMaService.getWxMaConfig().getTicket(type);
    }

    @Override
    public String getJsapiTicket() throws WxErrorException {
        return this.getJsapiTicket(false);
    }

    @Override
    public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
        final TicketType type = TicketType.JSAPI;
        Lock lock = this.wxMaService.getWxMaConfig().getTicketLock(type);
        try {
            lock.lock();
            if (forceRefresh) {
                this.wxMaService.getWxMaConfig().expireTicket(type);
            }

            if (this.wxMaService.getWxMaConfig().isTicketExpired(type)) {
                String responseContent = this.wxMaService.get(GET_JSAPI_TICKET_URL + "?type=jsapi", null);
                JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
                JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
                String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
                int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
                this.wxMaService.getWxMaConfig().updateTicket(type, jsapiTicket, expiresInSeconds);
            }
        } finally {
            lock.unlock();
        }
        return this.wxMaService.getWxMaConfig().getTicket(type);
    }

    @Override
    public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String randomStr = RandomUtils.getRandomStr();
        String jsapiTicket = getJsapiTicket(false);
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
