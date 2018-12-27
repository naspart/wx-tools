package com.rolbel.mp.api.impl;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.URIUtil;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpSubscribeMsgService;
import com.rolbel.mp.bean.subscribe.WxMpSubscribeMessage;
import com.rolbel.mp.config.WxMpConfig;

public class WxMpSubscribeMsgServiceImpl implements WxMpSubscribeMsgService {
    private WxMpService wxMpService;

    WxMpSubscribeMsgServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public String subscribeMsgAuthorizationUrl(String redirectURI, int scene, String reserved) {
        WxMpConfig wxMpConfig = this.wxMpService.getWxMpConfig();

        return String.format(SUBSCRIBE_MESSAGE_AUTHORIZE_URL, wxMpConfig.getAppId(), scene, wxMpConfig.getTemplateId(), URIUtil.encodeURIComponent(redirectURI), reserved);
    }

    @Override
    public void sendSubscribeMessage(WxMpSubscribeMessage message) throws WxErrorException {
        if (message.getTemplateId() == null) {
            message.setTemplateId(this.wxMpService.getWxMpConfig().getTemplateId());
        }

        this.wxMpService.post(SEND_MESSAGE_URL, message.toJson());
    }
}
