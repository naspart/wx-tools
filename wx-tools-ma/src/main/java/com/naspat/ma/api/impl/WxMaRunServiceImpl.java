package com.naspat.ma.api.impl;

import com.naspat.ma.api.WxMaRunService;
import com.naspat.ma.api.WxMaService;
import com.naspat.ma.bean.WxMaRunStepInfo;
import com.naspat.ma.util.crypt.WxMaCryptUtils;

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
