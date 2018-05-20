package com.rolbel.open.api.impl;

import com.rolbel.miniapp.api.impl.WxMaServiceImpl;
import com.rolbel.miniapp.bean.WxMaJscode2SessionResult;
import com.rolbel.miniapp.config.WxMaConfig;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.open.api.WxOpenComponentService;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
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
        return wxOpenComponentService.miniappJscode2Session(appId, jsCode);
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
