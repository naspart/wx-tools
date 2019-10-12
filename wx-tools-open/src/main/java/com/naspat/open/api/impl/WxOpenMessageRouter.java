package com.naspat.open.api.impl;

import com.naspat.mp.api.WxMpMessageRouter;
import com.naspat.mp.bean.message.WxMpXmlMessage;
import com.naspat.mp.bean.message.WxMpXmlOutMessage;
import com.naspat.open.api.WxOpenService;

import java.util.HashMap;
import java.util.Map;

public class WxOpenMessageRouter extends WxMpMessageRouter {
    private WxOpenService wxOpenService;

    public WxOpenMessageRouter(WxOpenService wxOpenService) {
        super(null);
        this.wxOpenService = wxOpenService;
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, String appId) {
        return route(wxMessage, new HashMap<>(), appId);
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, final Map<String, Object> context, String appId) {
        return route(wxMessage, context, wxOpenService.getWxOpenComponentService().getWxMpServiceByAppId(appId));
    }
}
