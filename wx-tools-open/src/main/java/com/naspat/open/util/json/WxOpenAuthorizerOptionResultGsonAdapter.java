package com.naspat.open.util.json;

import com.google.gson.*;
import com.naspat.common.util.json.GsonHelper;
import com.naspat.open.bean.result.WxOpenAuthorizerOptionResult;

import java.lang.reflect.Type;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenAuthorizerOptionResultGsonAdapter implements JsonDeserializer<WxOpenAuthorizerOptionResult> {
  @Override
  public WxOpenAuthorizerOptionResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxOpenAuthorizerOptionResult authorizerOptionResult = new WxOpenAuthorizerOptionResult();
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    authorizerOptionResult.setAuthorizerAppid(GsonHelper.getString(jsonObject, "authorizer_appid"));
    authorizerOptionResult.setOptionName(GsonHelper.getString(jsonObject, "option_name"));
    authorizerOptionResult.setOptionValue(GsonHelper.getString(jsonObject, "option_value"));
    return authorizerOptionResult;
  }
}
