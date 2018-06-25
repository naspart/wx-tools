package com.rolbel.mp.util.json.adapter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.util.json.GsonHelper;
import com.rolbel.mp.bean.wx_card.WxMpCardInfo;
import com.rolbel.mp.bean.result.WxMpCardResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;

import java.lang.reflect.Type;

public class WxMpCardResultGsonAdapter implements JsonDeserializer<WxMpCardResult> {
    @Override
    public WxMpCardResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxMpCardResult cardResult = new WxMpCardResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        cardResult.setOpenId(GsonHelper.getString(jsonObject, "openid"));
        cardResult.setCanConsume(GsonHelper.getBoolean(jsonObject, "can_consume"));
        cardResult.setUserCardStatus(GsonHelper.getString(jsonObject, "user_card_status"));

        WxMpCardInfo card = WxMpGsonBuilder.INSTANCE.create().fromJson(jsonObject.get("wx_card"),
                new TypeToken<WxMpCardInfo>() {
                }.getType());

        cardResult.setCard(card);

        return cardResult;
    }
}
