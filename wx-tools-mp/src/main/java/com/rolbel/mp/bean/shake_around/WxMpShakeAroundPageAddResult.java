package com.rolbel.mp.bean.shake_around;

import com.google.gson.JsonObject;
import com.rolbel.common.util.json.GsonHelper;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundPageAddResult implements Serializable {
    private Integer errorCode;
    private String errorMsg;
    private Integer pageId;

    public static WxMpShakeAroundPageAddResult fromJson(String json) {
        JsonObject jsonObject = WxMpGsonBuilder.INSTANCE.create().fromJson(json, JsonObject.class);
        WxMpShakeAroundPageAddResult result = new WxMpShakeAroundPageAddResult();
        result.setErrorCode(GsonHelper.getInteger(jsonObject, "errcode"));
        result.setErrorMsg(GsonHelper.getString(jsonObject, "errmsg"));
        jsonObject = jsonObject.getAsJsonObject("data");
        if (jsonObject != null) {
            result.setPageId(GsonHelper.getInteger(jsonObject, "page_id"));
        }
        return result;
    }
}
