package com.naspat.mp.bean.shake_around;

import com.google.gson.JsonObject;
import com.naspat.common.util.json.GsonHelper;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundPageAddResult implements Serializable {
    private static final long serialVersionUID = -8427134312669855977L;

    private Integer errorCode;
    private String errorMsg;
    private Integer pageId;

    public static WxMpShakeAroundPageAddResult fromJson(String json) {
        JsonObject jsonObject = WxMpGsonBuilder.create().fromJson(json, JsonObject.class);
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
