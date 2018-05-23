package com.rolbel.mp.api.impl;

import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.util.http.URIUtil;
import com.rolbel.mp.api.WxMpConfigStorage;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpSubscribeMsgService;
import com.rolbel.mp.bean.subscribe.WxMpSubscribeMessage;

public class WxMpSubscribeMsgServiceImpl implements WxMpSubscribeMsgService {
    private WxMpService wxMpService;

    public WxMpSubscribeMsgServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public String subscribeMsgAuthorizationUrl(String redirectURI, int scene, String reserved) {
        WxMpConfigStorage storage = this.wxMpService.getWxMpConfigStorage();

        return String.format(SUBSCRIBE_MESSAGE_AUTHORIZE_URL, storage.getAppId(), scene, storage.getTemplateId(), URIUtil.encodeURIComponent(redirectURI), reserved);
    }

    @Override
    public void sendSubscribeMessage(WxMpSubscribeMessage message) throws WxErrorException {
        if (message.getTemplateId() == null) {
            message.setTemplateId(this.wxMpService.getWxMpConfigStorage().getTemplateId());
        }

        this.wxMpService.post(SEND_MESSAGE_URL, message.toJson());
    }
}
