package com.naspat.open.util.json;

import com.google.gson.*;
import com.naspat.common.util.json.GsonHelper;
import com.naspat.open.bean.WxOpenComponentAccessToken;

import java.lang.reflect.Type;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenComponentAccessTokenGsonAdapter implements JsonDeserializer<WxOpenComponentAccessToken> {
  @Override
  public WxOpenComponentAccessToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenComponentAccessToken componentAccessToken = new WxOpenComponentAccessToken();
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    componentAccessToken.setComponentAccessToken(GsonHelper.getString(jsonObject, "component_access_token"));
    componentAccessToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
    return componentAccessToken;
  }
}
