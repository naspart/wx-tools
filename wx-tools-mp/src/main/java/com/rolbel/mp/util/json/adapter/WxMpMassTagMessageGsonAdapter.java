package com.rolbel.mp.util.json.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rolbel.common.api.WxConstant;
import com.rolbel.mp.bean.WxMpMassTagMessage;

import java.lang.reflect.Type;

public class WxMpMassTagMessageGsonAdapter implements JsonSerializer<WxMpMassTagMessage> {

    @Override
    public JsonElement serialize(WxMpMassTagMessage message, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject messageJson = new JsonObject();

        JsonObject filter = new JsonObject();
        if (message.isSendAll() || null == message.getTagId()) {
            filter.addProperty("is_to_all", true);
        } else {
            filter.addProperty("is_to_all", false);
            filter.addProperty("tag_id", message.getTagId());
        }
        messageJson.add("filter", filter);

        if (WxConstant.MassMsgType.MPNEWS.equals(message.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", message.getMediaId());
            messageJson.add(WxConstant.MassMsgType.MPNEWS, sub);
        }
        if (WxConstant.MassMsgType.TEXT.equals(message.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("content", message.getContent());
            messageJson.add(WxConstant.MassMsgType.TEXT, sub);
        }
        if (WxConstant.MassMsgType.VOICE.equals(message.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", message.getMediaId());
            messageJson.add(WxConstant.MassMsgType.VOICE, sub);
        }
        if (WxConstant.MassMsgType.IMAGE.equals(message.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", message.getMediaId());
            messageJson.add(WxConstant.MassMsgType.IMAGE, sub);
        }
        if (WxConstant.MassMsgType.MPVIDEO.equals(message.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", message.getMediaId());
            messageJson.add(WxConstant.MassMsgType.MPVIDEO, sub);
        }
        messageJson.addProperty("msgtype", message.getMsgType());
        messageJson.addProperty("send_ignore_reprint", message.isSendIgnoreReprint() ? 0 : 1);
        return messageJson;
    }
}
