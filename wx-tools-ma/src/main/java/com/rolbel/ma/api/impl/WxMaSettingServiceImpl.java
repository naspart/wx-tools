package com.rolbel.ma.api.impl;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.api.WxMaSettingService;
import com.rolbel.ma.bean.WxMaDomainAction;
import com.rolbel.ma.util.json.WxMaGsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class WxMaSettingServiceImpl implements WxMaSettingService {
    private WxMaService wxMaService;

    public WxMaSettingServiceImpl(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    @Override
    public WxMaDomainAction modifyDomain(WxMaDomainAction domainAction) throws WxErrorException {
        String responseContent = this.wxMaService.post(MODIFY_DOMAIN_URL, domainAction.toJson());
        return WxMaDomainAction.fromJson(responseContent);
    }

    @Override
    public WxMaDomainAction setWebViewDomain(WxMaDomainAction domainAction) throws WxErrorException {
        String responseContent = this.wxMaService.post(SET_WEB_VIEW_DOMAIN_URL, domainAction.toJson());
        return WxMaDomainAction.fromJson(responseContent);
    }

    @Override
    public void bindTester(String wechatId) throws WxErrorException {
        Map<String, Object> param = new HashMap<>(1);
        param.put("wechatid", wechatId);
        this.wxMaService.post(BIND_TESTER_URL, WxMaGsonBuilder.create().toJson(param));
    }

    @Override
    public void unbindTester(String wechatId) throws WxErrorException {
        Map<String, Object> param = new HashMap<>(1);
        param.put("wechatid", wechatId);
        this.wxMaService.post(UNBIND_TESTER_URL, WxMaGsonBuilder.create().toJson(param));
    }
}
