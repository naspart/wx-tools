package com.naspat.mp.util.json.adapter;

import com.google.gson.*;
import com.naspat.common.util.json.GsonHelper;
import com.naspat.mp.bean.card.WxMpCard;

import java.lang.reflect.Type;

public class WxMpCardGsonAdapter implements JsonDeserializer<WxMpCard> {

    @Override
    public WxMpCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxMpCard card = new WxMpCard();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        card.setCardId(GsonHelper.getString(jsonObject, "card_id"));
        card.setBeginTime(GsonHelper.getLong(jsonObject, "begin_time"));
        card.setEndTime(GsonHelper.getLong(jsonObject, "end_time"));

        return card;
    }
}
