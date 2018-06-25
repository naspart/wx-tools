package com.rolbel.open.api.impl;

import com.rolbel.ma.api.impl.WxMaServiceImpl;
import com.rolbel.ma.bean.WxMaJscode2SessionResult;
import com.rolbel.ma.config.WxMaConfig;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.open.api.WxOpenComponentService;

/* package */ class WxOpenMaServiceImpl extends WxMaServiceImpl {
    private WxOpenComponentService wxOpenComponentService;
    private WxMaConfig wxMaConfig;
    private String appId;

    public WxOpenMaServiceImpl(WxOpenComponentService wxOpenComponentService, String appId, WxMaConfig wxMaConfig) {
        this.wxOpenComponentService = wxOpenComponentService;
        this.appId = appId;
        this.wxMaConfig = wxMaConfig;
        initHttp();
    }

    @Override
    public WxMaJscode2SessionResult jsCode2SessionInfo(String jsCode) throws WxErrorException {
        return wxOpenComponentService.maJscode2Session(appId, jsCode);
    }

    @Override
    public WxMaConfig getWxMaConfig() {
        return wxMaConfig;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        return wxOpenComponentService.getAuthorizerAccessToken(appId, forceRefresh);
    }
}
