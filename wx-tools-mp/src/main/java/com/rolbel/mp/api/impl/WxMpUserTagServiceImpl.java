package com.rolbel.mp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.bean.result.WxError;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpUserTagService;
import com.rolbel.mp.bean.tag.WxTagListUser;
import com.rolbel.mp.bean.tag.WxUserTag;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Binary Wang on 2016/9/2.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
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
    public Boolean tagUpdate(Long id, String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(WxMpUserTagService.UPDATE_TAG_URL, json.toString());
        WxError wxError = WxError.fromJson(responseContent);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public Boolean tagDelete(Long id) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(WxMpUserTagService.DELETE_TAG_URL, json.toString());
        WxError wxError = WxError.fromJson(responseContent);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public WxTagListUser tagListUser(Long tagId, String nextOpenid) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        json.addProperty("next_openid", StringUtils.trimToEmpty(nextOpenid));

        String responseContent = this.wxMpService.post(WxMpUserTagService.GET_TAG_USER_LIST_URL, json.toString());
        return WxTagListUser.fromJson(responseContent);
    }

    @Override
    public boolean batchTagging(Long tagId, String[] openids) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openids) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        String responseContent = this.wxMpService.post(WxMpUserTagService.BATCH_SET_TAG_FOR_USERS_URL, json.toString());
        WxError wxError = WxError.fromJson(responseContent);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public boolean batchUntagging(Long tagId, String[] openids) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openids) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        String responseContent = this.wxMpService.post(WxMpUserTagService.BATCH_CANCEL_TAG_FOR_USERS_URL, json.toString());
        WxError wxError = WxError.fromJson(responseContent);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public List<Long> userTagList(String openid) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);

        String responseContent = this.wxMpService.post(WxMpUserTagService.GET_USER_TAG_LIST_URL, json.toString());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent).getAsJsonObject().get("tagid_list"),
                new TypeToken<List<Long>>() {
                }.getType());
    }
}
