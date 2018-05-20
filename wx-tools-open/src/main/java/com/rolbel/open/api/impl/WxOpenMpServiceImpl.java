package com.rolbel.open.api.impl;

import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.api.WxMpConfigStorage;
import com.rolbel.mp.api.impl.WxMpServiceImpl;
import com.rolbel.mp.bean.result.WxMpOAuth2AccessToken;
import com.rolbel.open.api.WxOpenComponentService;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
/* package */ class WxOpenMpServiceImpl extends WxMpServiceImpl {
    private WxOpenComponentService wxOpenComponentService;
    private WxMpConfigStorage wxMpConfigStorage;
    private String appId;

    public WxOpenMpServiceImpl(WxOpenComponentService wxOpenComponentService, String appId, WxMpConfigStorage wxMpConfigStorage) {
        this.wxOpenComponentService = wxOpenComponentService;
        this.appId = appId;
        this.wxMpConfigStorage = wxMpConfigStorage;
        initHttp();
    }

    @Override
    public WxMpConfigStorage getWxMpConfigStorage() {
        return wxMpConfigStorage;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        return wxOpenComponentService.getAuthorizerAccessToken(appId, forceRefresh);
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
