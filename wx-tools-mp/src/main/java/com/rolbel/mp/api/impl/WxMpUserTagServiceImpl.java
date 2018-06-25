package com.rolbel.mp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpUserTagService;
import com.rolbel.mp.bean.tag.WxTagListUser;
import com.rolbel.mp.bean.tag.WxUserTag;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class WxMpUserTagServiceImpl implements WxMpUserTagService {
    private WxMpService wxMpService;

    public WxMpUserTagServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxUserTag tagCreate(String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(WxMpUserTagService.CREATE_TAG_URL, json.toString());

        return WxUserTag.fromJson(responseContent);
    }

    @Override
    public List<WxUserTag> tagGet() throws WxErrorException {
        String responseContent = this.wxMpService.get(WxMpUserTagService.GET_TAG_URL, null);

        return WxUserTag.listFromJson(responseContent);
    }

    @Override
    public void tagUpdate(Long id, String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        this.wxMpService.post(WxMpUserTagService.UPDATE_TAG_URL, json.toString());
    }

    @Override
    public void tagDelete(Long id) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        json.add("tag", tagJson);

        this.wxMpService.post(WxMpUserTagService.DELETE_TAG_URL, json.toString());
    }

    @Override
    public WxTagListUser tagListUser(Long tagId, String nextOpenId) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        json.addProperty("next_openid", StringUtils.trimToEmpty(nextOpenId));

        String responseContent = this.wxMpService.post(WxMpUserTagService.GET_TAG_USER_LIST_URL, json.toString());

        return WxTagListUser.fromJson(responseContent);
    }

    @Override
    public void batchTagging(Long tagId, String[] openIds) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openIds) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        this.wxMpService.post(WxMpUserTagService.BATCH_SET_TAG_FOR_USERS_URL, json.toString());
    }

    @Override
    public void batchUntagging(Long tagId, String[] openIds) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openIds) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        this.wxMpService.post(WxMpUserTagService.BATCH_CANCEL_TAG_FOR_USERS_URL, json.toString());
    }

    @Override
    public List<Long> userTagList(String openId) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("openid", openId);

        String responseContent = this.wxMpService.post(WxMpUserTagService.GET_USER_TAG_LIST_URL, json.toString());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent).getAsJsonObject().get("tagid_list"),
                new TypeToken<List<Long>>() {
                }.getType());
    }
}
