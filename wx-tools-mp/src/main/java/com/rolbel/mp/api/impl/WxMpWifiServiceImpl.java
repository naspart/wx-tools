package com.rolbel.mp.api.impl;

import com.google.gson.JsonObject;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpWifiService;
import com.rolbel.mp.bean.wifi.WxMpWifiShopListResult;

/**
 * <pre>
 *  Created by BinaryWang on 2018/6/10.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMpWifiServiceImpl implements WxMpWifiService {
    private WxMpService wxMpService;

    public WxMpWifiServiceImpl(WxMpService wxMpService) {
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
