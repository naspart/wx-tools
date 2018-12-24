package com.rolbel.mp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpTemplateMsgService;
import com.rolbel.mp.bean.template.WxMpTemplate;
import com.rolbel.mp.bean.template.WxMpTemplateIndustry;
import com.rolbel.mp.bean.template.WxMpTemplateMessage;
import com.rolbel.mp.bean.template.result.WxMpTemplateAddResult;
import com.rolbel.mp.bean.template.result.WxMpTemplateSendMsgResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;

import java.util.List;


public class WxMpTemplateMsgServiceImpl implements WxMpTemplateMsgService {
    private WxMpService wxMpService;

    WxMpTemplateMsgServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public void setIndustry(WxMpTemplateIndustry wxMpIndustry) throws WxErrorException {
        if (null == wxMpIndustry.getPrimaryIndustry() || null == wxMpIndustry.getPrimaryIndustry().getId()
                || null == wxMpIndustry.getSecondIndustry() || null == wxMpIndustry.getSecondIndustry().getId()) {
            throw new IllegalArgumentException("行业Id不能为空，请核实");
        }

        this.wxMpService.post(SET_TEMPLATE_INDUSTRY_URL, wxMpIndustry.toJson());
    }

    @Override
    public WxMpTemplateIndustry getIndustry() throws WxErrorException {
        String responseContent = this.wxMpService.get(GET_TEMPLATE_INDUSTRY_URL, null);

        return WxMpTemplateIndustry.fromJson(responseContent);
    }

    @Override
    public WxMpTemplateSendMsgResult sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException {
        String responseContent = this.wxMpService.post(SEND_TEMPLATE_MSG_URL, templateMessage.toJson());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpTemplateSendMsgResult>() {
                }.getType());
    }

    @Override
    public WxMpTemplateAddResult addTemplate(String shortTemplateId) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("template_id_short", shortTemplateId);

        String responseContent = this.wxMpService.post(ADD_TEMPLATE_URL, jsonObject.toString());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpTemplateAddResult>() {
                }.getType());
    }

    @Override
    public List<WxMpTemplate> getAllPrivateTemplate() throws WxErrorException {
        return WxMpTemplate.fromJson(this.wxMpService.get(GET_ALL_PRIVATE_TEMPLATE_URL, null));
    }

    @Override
    public void delPrivateTemplate(String templateId) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("template_id", templateId);

        this.wxMpService.post(DELETE_PRIVATE_TEMPLATE_URL, jsonObject.toString());
    }
}
