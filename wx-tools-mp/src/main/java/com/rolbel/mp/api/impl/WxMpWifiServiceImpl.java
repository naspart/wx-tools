package com.rolbel.mp.api.impl;

import com.google.gson.JsonObject;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpWifiService;
import com.rolbel.mp.bean.wifi.WxMpWifiShopListResult;

public class WxMpWifiServiceImpl implements WxMpWifiService {
    private WxMpService wxMpService;

    WxMpWifiServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpWifiShopListResult listShop(int pageIndex, int pageSize) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("pageindex", pageIndex);
        json.addProperty("pagesize", pageSize);
        final String result = this.wxMpService.post("https://api.weixin.qq.com/bizwifi/shop/list", json.toString());

        return WxMpWifiShopListResult.fromJson(result);
    }
}
