/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.rolbel.miniapp.util.json;

import com.rolbel.miniapp.bean.WxMaKefuMessage;
import com.rolbel.miniapp.constant.WxMaConstant;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMaKefuMessageGsonAdapter implements JsonSerializer<WxMaKefuMessage> {

  @Override
  public JsonElement serialize(WxMaKefuMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    messageJson.addProperty("touser", message.getToUser());
    messageJson.addProperty("msgtype", message.getMsgType());

    if (WxMaConstant.KefuMsgType.TEXT.equals(message.getMsgType())) {
      JsonObject text = new JsonObject();
      text.addProperty("content", message.getContent());
      messageJson.add("text", text);
    }

    if (WxMaConstant.KefuMsgType.IMAGE.equals(message.getMsgType())) {
      JsonObject image = new JsonObject();
      image.addProperty("media_id", message.getMediaId());
      messageJson.add("image", image);
    }

    return messageJson;
  }

}
