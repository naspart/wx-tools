package com.naspat.ma.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.ma.api.WxMaMsgService;
import com.naspat.ma.api.WxMaService;
import com.naspat.ma.bean.WxMaKefuMessage;
import com.naspat.ma.bean.WxMaTemplateMessage;
import com.naspat.ma.bean.WxMaUniformMessage;
import com.naspat.ma.constant.WxMaConstant;

public class WxMaMsgServiceImpl implements WxMaMsgService {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private WxMaService wxMaService;

    public WxMaMsgServiceImpl(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    @Override
    public boolean sendKefuMsg(WxMaKefuMessage message) throws WxErrorException {
        String responseContent = this.wxMaService.post(KEFU_MESSAGE_SEND_URL, message.toJson());
        return responseContent != null;
    }

    @Override
    public void sendTemplateMsg(WxMaTemplateMessage templateMessage) throws WxErrorException {
        String responseContent = this.wxMaService.post(TEMPLATE_MSG_SEND_URL, templateMessage.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get(WxMaConstant.ERRCODE).getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent));
        }
    }

    @Override
    public void sendUniformMsg(WxMaUniformMessage uniformMessage) throws WxErrorException {
        String responseContent = this.wxMaService.post(UNIFORM_MSG_SEND_URL, uniformMessage.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get(WxMaConstant.ERRCODE).getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent));
        }
    }
}
