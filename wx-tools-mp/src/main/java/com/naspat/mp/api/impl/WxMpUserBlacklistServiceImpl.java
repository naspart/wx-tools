package com.naspat.mp.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.naspat.common.error.WxErrorException;
import com.naspat.mp.api.WxMpService;
import com.naspat.mp.api.WxMpUserBlacklistService;
import com.naspat.mp.bean.user.WxMpUserBlacklistGetResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxMpUserBlacklistServiceImpl implements WxMpUserBlacklistService {
    private WxMpService wxMpService;

    WxMpUserBlacklistServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpUserBlacklistGetResult getBlacklist(String nextOpenid) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("begin_openid", nextOpenid);

        String responseContent = this.wxMpService.post(GET_USER_BLACK_LIST_URL, jsonObject.toString());

        return WxMpUserBlacklistGetResult.fromJson(responseContent);
    }

    @Override
    public void pushToBlacklist(List<String> openidList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>();
        map.put("openid_list", openidList);

        this.wxMpService.post(BATCH_ADD_BLACK_LIST_URL, new Gson().toJson(map));
    }

    @Override
    public void pullFromBlacklist(List<String> openidList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>();
        map.put("openid_list", openidList);

        this.wxMpService.post(BATCH_DELETE_BLACK_LIST_URL, new Gson().toJson(map));
    }
}
