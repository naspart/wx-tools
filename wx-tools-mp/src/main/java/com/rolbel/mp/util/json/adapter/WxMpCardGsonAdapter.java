package com.rolbel.mp.util.json.adapter;

import com.google.gson.*;
import com.rolbel.common.util.json.GsonHelper;
import com.rolbel.mp.bean.card.WxMpCardInfo;

import java.lang.reflect.Type;

public class WxMpCardGsonAdapter implements JsonDeserializer<WxMpCardInfo> {

    @Override
    public WxMpCardInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxMpCardInfo card = new WxMpCardInfo();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        card.setCardId(GsonHelper.getString(jsonObject, "card_id"));
        card.setBeginTime(GsonHelper.getLong(jsonObject, "begin_time"));
        card.setEndTime(GsonHelper.getLong(jsonObject, "end_time"));

        return card;
    }
}
