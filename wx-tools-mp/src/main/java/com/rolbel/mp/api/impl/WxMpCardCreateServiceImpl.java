package com.rolbel.mp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.api.WxMpCardCreateService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.card.base.WxMpCardCreateRequest;
import com.rolbel.mp.bean.card.result.WxMpCardCreateResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxMpCardCreateServiceImpl implements WxMpCardCreateService {
    private final Logger log = LoggerFactory.getLogger(WxMpCardServiceImpl.class);

    private WxMpService wxMpService;

    public WxMpCardCreateServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpService getWxMpService() {
        return this.wxMpService;
    }

    @Override
    public WxMpCardCreateResult createCard(WxMpCardCreateRequest wxMpGrouponCard) throws WxErrorException {
        String responseContent = this.wxMpService.post(WxMpCardCreateService.CARD_CREATE_URL, wxMpGrouponCard.toJson());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);

        return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement,
                new TypeToken<WxMpCardCreateResult>() {
                }.getType());
    }
}
