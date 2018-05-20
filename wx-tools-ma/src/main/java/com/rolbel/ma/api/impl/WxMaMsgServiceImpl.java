package com.rolbel.ma.api.impl;

import com.rolbel.ma.api.WxMaMsgService;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.bean.WxMaKefuMessage;
import com.rolbel.ma.bean.WxMaTemplateMessage;
import com.rolbel.ma.constant.WxMaConstant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rolbel.common.bean.result.WxError;
import com.rolbel.common.exception.WxErrorException;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
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

}
