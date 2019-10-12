package com.naspat.mp.api.impl;

import com.google.gson.JsonObject;
import com.naspat.common.error.WxErrorException;
import com.naspat.mp.api.WxMpService;
import com.naspat.mp.api.WxMpUserService;
import com.naspat.mp.bean.user.WxMpChangeOpenid;
import com.naspat.mp.bean.user.WxMpUser;
import com.naspat.mp.bean.user.WxMpUserList;
import com.naspat.mp.bean.user.WxMpUserQuery;
import com.naspat.mp.util.json.WxMpGsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxMpUserServiceImpl implements WxMpUserService {
    private WxMpService wxMpService;

    WxMpUserServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public void userUpdateRemark(String openId, String remark) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("openid", openId);
        json.addProperty("remark", remark);

        this.wxMpService.post(USER_INFO_UPDATE_REMARK_URL, json.toString());
    }

    @Override
    public WxMpUser userInfo(String openid) throws WxErrorException {
        return this.userInfo(openid, null);
    }

    @Override
    public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
        lang = lang == null ? "zh_CN" : lang;
        String responseContent = this.wxMpService.get(USER_INFO_URL, "openid=" + openid + "&lang=" + lang);

        return WxMpUser.fromJson(responseContent);
    }

    @Override
    public WxMpUserList userList(String next_openid) throws WxErrorException {
        String responseContent = this.wxMpService.get(USER_GET_URL, next_openid == null ? null : "next_openid=" + next_openid);

        return WxMpUserList.fromJson(responseContent);
    }

    @Override
    public List<WxMpUser> userInfoList(List<String> openIds) throws WxErrorException {
        return this.userInfoList(new WxMpUserQuery(openIds));
    }

    @Override
    public List<WxMpUser> userInfoList(WxMpUserQuery userQuery) throws WxErrorException {
        String responseContent = this.wxMpService.post(USER_INFO_BATCH_GET_URL, userQuery.toJsonString());

        return WxMpUser.fromJsonList(responseContent);
    }

    @Override
    public List<WxMpChangeOpenid> changeOpenid(String fromAppid, List<String> openidList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>();
        map.put("from_appid", fromAppid);
        map.put("openid_list", openidList);
        String responseContent = this.wxMpService.post(USER_CHANGE_OPENID_URL, WxMpGsonBuilder.create().toJson(map));

        return WxMpChangeOpenid.fromJsonList(responseContent);
    }
}
