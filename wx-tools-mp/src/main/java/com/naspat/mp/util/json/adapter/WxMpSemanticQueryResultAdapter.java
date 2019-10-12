package com.naspat.mp.util.json.adapter;

import com.google.gson.*;
import com.naspat.common.util.json.GsonHelper;
import com.naspat.mp.bean.WxMpSemanticQueryResult;

import java.lang.reflect.Type;

public class WxMpSemanticQueryResultAdapter implements JsonDeserializer<WxMpSemanticQueryResult> {

    @Override
    public WxMpSemanticQueryResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMpSemanticQueryResult result = new WxMpSemanticQueryResult();
        JsonObject resultJsonObject = json.getAsJsonObject();

        if (GsonHelper.getString(resultJsonObject, "query") != null) {
            result.setQuery(GsonHelper.getString(resultJsonObject, "query"));
        }
        if (GsonHelper.getString(resultJsonObject, "type") != null) {
            result.setType(GsonHelper.getString(resultJsonObject, "type"));
        }
        if (resultJsonObject.get("semantic") != null) {
            result.setSemantic(resultJsonObject.get("semantic").toString());
        }
        if (resultJsonObject.get("result") != null) {
            result.setResult(resultJsonObject.get("result").toString());
        }
        if (GsonHelper.getString(resultJsonObject, "answer") != null) {
            result.setAnswer(GsonHelper.getString(resultJsonObject, "answer"));
        }
        if (GsonHelper.getString(resultJsonObject, "text") != null) {
            result.setText(GsonHelper.getString(resultJsonObject, "text"));
        }
        return result;
    }
}
