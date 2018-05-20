package com.rolbel.miniapp.api.impl;

import com.rolbel.miniapp.api.WxMaMsgService;
import com.rolbel.miniapp.api.WxMaService;
import com.rolbel.miniapp.bean.WxMaKefuMessage;
import com.rolbel.miniapp.bean.WxMaTemplateMessage;
import com.rolbel.miniapp.constant.WxMaConstant;
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
