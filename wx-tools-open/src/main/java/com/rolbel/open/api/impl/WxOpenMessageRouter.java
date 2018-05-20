package com.rolbel.open.api.impl;

import com.rolbel.mp.api.WxMpMessageRouter;
import com.rolbel.mp.bean.message.WxMpXmlMessage;
import com.rolbel.mp.bean.message.WxMpXmlOutMessage;
import com.rolbel.open.api.WxOpenService;

import java.util.HashMap;
import java.util.Map;

public class WxOpenMessageRouter extends WxMpMessageRouter {
    private WxOpenService wxOpenService;

    public WxOpenMessageRouter(WxOpenService wxOpenService) {
        super(null);
        this.wxOpenService = wxOpenService;
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, String appId) {
        return route(wxMessage, new HashMap<String, Object>(), appId);
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, final Map<String, Object> context, String appId) {
        return route(wxMessage, context, wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId));
    }
}
