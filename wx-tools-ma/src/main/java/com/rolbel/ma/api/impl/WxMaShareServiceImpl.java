package com.rolbel.ma.api.impl;

import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.api.WxMaShareService;
import com.rolbel.ma.bean.WxMaShareInfo;
import com.rolbel.ma.util.crypt.WxMaCryptUtils;

public class WxMaShareServiceImpl implements WxMaShareService {
    private WxMaService service;

    public WxMaShareServiceImpl(WxMaService service) {
        this.service = service;
    }

    @Override
    public WxMaShareInfo getShareInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxMaShareInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
    }
}
