package com.naspat.open.api.impl;

import com.naspat.common.error.WxErrorException;
import com.naspat.mp.api.impl.WxMpServiceImpl;
import com.naspat.mp.bean.WxMpOAuth2AccessToken;
import com.naspat.mp.config.WxMpConfig;
import com.naspat.open.api.WxOpenComponentService;

public class WxOpenMpServiceImpl extends WxMpServiceImpl {
    private WxOpenComponentService wxOpenComponentService;
    private WxMpConfig wxMpConfig;
    private String appId;

    WxOpenMpServiceImpl(WxOpenComponentService wxOpenComponentService, String appId, WxMpConfig wxMpConfig) {
        this.wxOpenComponentService = wxOpenComponentService;
        this.appId = appId;
        this.wxMpConfig = wxMpConfig;
        initHttp();
    }

    @Override
    public WxMpConfig getWxMpConfig() {
        return wxMpConfig;
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
        return wxOpenComponentService.oauth2getAccessToken(appId, code);
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException {
        return wxOpenComponentService.oauth2refreshAccessToken(appId, refreshToken);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        return wxOpenComponentService.oauth2buildAuthorizationUrl(appId, redirectURI, scope, state);
    }
}
