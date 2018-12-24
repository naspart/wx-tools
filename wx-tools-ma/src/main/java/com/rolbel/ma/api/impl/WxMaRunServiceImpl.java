package com.rolbel.ma.api.impl;

import com.rolbel.ma.api.WxMaRunService;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.bean.WxMaRunStepInfo;
import com.rolbel.ma.util.crypt.WxMaCryptUtils;

import java.util.List;

public class WxMaRunServiceImpl implements WxMaRunService {
    private WxMaService service;

    public WxMaRunServiceImpl(WxMaService service) {
        this.service = service;
    }

    @Override
    public List<WxMaRunStepInfo> getRunStepInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxMaRunStepInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
    }
}
