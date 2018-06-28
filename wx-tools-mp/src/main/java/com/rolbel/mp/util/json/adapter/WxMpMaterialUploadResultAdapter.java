package com.rolbel.mp.util.json.adapter;

import com.google.gson.*;
import com.rolbel.common.util.json.GsonHelper;
import com.rolbel.mp.bean.material.WxMpMaterialUploadResult;

import java.lang.reflect.Type;

public class WxMpMaterialUploadResultAdapter implements JsonDeserializer<WxMpMaterialUploadResult> {

    @Override
    public WxMpMaterialUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMpMaterialUploadResult uploadResult = new WxMpMaterialUploadResult();
        JsonObject uploadResultJsonObject = json.getAsJsonObject();

        if (uploadResultJsonObject.get("url") != null && !uploadResultJsonObject.get("url").isJsonNull()) {
            uploadResult.setUrl(GsonHelper.getAsString(uploadResultJsonObject.get("url")));
        }
        if (uploadResultJsonObject.get("media_id") != null && !uploadResultJsonObject.get("media_id").isJsonNull()) {
            uploadResult.setMediaId(GsonHelper.getAsString(uploadResultJsonObject.get("media_id")));
        }
        return uploadResult;
    }
}
