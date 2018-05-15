package com.rolbel.mp.util.json.adapter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.util.json.GsonHelper;
import com.rolbel.mp.bean.WxMpCard;
import com.rolbel.mp.bean.result.WxMpCardResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by YuJian on 15/11/11.
 *
 * @author YuJian
 * @version 15/11/11
 */
public class WxMpCardResultGsonAdapter implements JsonDeserializer<WxMpCardResult> {
    @Override
    public WxMpCardResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxMpCardResult cardResult = new WxMpCardResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        cardResult.setOpenId(GsonHelper.getString(jsonObject, "openid"));
        cardResult.setErrorCode(GsonHelper.getString(jsonObject, "errcode"));
        cardResult.setErrorMsg(GsonHelper.getString(jsonObject, "errmsg"));
        cardResult.setCanConsume(GsonHelper.getBoolean(jsonObject, "can_consume"));
        cardResult.setUserCardStatus(GsonHelper.getString(jsonObject, "user_card_status"));

        WxMpCard card = WxMpGsonBuilder.INSTANCE.create().fromJson(jsonObject.get("card"),
                new TypeToken<WxMpCard>() {
                }.getType());

        cardResult.setCard(card);

        return cardResult;
    }
}
