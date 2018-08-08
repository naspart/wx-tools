package com.rolbel.mp.api.impl;

import com.google.gson.JsonObject;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpUserService;
import com.rolbel.mp.bean.user.WxMpUserQuery;
import com.rolbel.mp.bean.user.WxMpUser;
import com.rolbel.mp.bean.user.WxMpUserList;

import java.util.List;

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

        this.wxMpService.post(UPDATE_USER_REMARK_URL, json.toString());
    }

    @Override
    public WxMpUser userInfo(String openid) throws WxErrorException {
        return this.userInfo(openid, null);
    }

    @Override
    public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
        lang = lang == null ? "zh_CN" : lang;
        String responseContent = this.wxMpService.get(GET_USER_INFO_URL, "openid=" + openid + "&lang=" + lang);

        return WxMpUser.fromJson(responseContent);
    }

    @Override
    public WxMpUserList userList(String next_openid) throws WxErrorException {
        String responseContent = this.wxMpService.get(GET_USER_LIST_URL, next_openid == null ? null : "next_openid=" + next_openid);

        return WxMpUserList.fromJson(responseContent);
    }

    @Override
    public List<WxMpUser> userInfoList(List<String> openIds) throws WxErrorException {
        return this.userInfoList(new WxMpUserQuery(openIds));
    }

    @Override
    public List<WxMpUser> userInfoList(WxMpUserQuery userQuery) throws WxErrorException {
        String responseContent = this.wxMpService.post(BATCH_GET_USER_INFO_URL, userQuery.toJsonString());

        return WxMpUser.fromJsonList(responseContent);
    }
}
