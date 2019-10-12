package com.naspat.ma.api.impl;

import com.naspat.ma.api.WxMaService;
import com.naspat.ma.api.WxMaShareService;
import com.naspat.ma.bean.WxMaShareInfo;
import com.naspat.ma.util.crypt.WxMaCryptUtils;

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
