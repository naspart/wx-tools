package com.naspat.mp.util.json.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.naspat.common.api.WxConstant;
import com.naspat.mp.bean.WxMpMassPreviewMessage;

import java.lang.reflect.Type;

public class WxMpMassPreviewMessageGsonAdapter implements JsonSerializer<WxMpMassPreviewMessage> {
    @Override
    public JsonElement serialize(WxMpMassPreviewMessage wxMpMassPreviewMessage, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("towxname", wxMpMassPreviewMessage.getToWxUserName());
        jsonObject.addProperty("touser", wxMpMassPreviewMessage.getToWxUserOpenid());
        if (WxConstant.MassMsgType.MPNEWS.equals(wxMpMassPreviewMessage.getMsgType())) {
            JsonObject news = new JsonObject();
            news.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
            jsonObject.add(WxConstant.MassMsgType.MPNEWS, news);
        }
        if (WxConstant.MassMsgType.TEXT.equals(wxMpMassPreviewMessage.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("content", wxMpMassPreviewMessage.getContent());
            jsonObject.add(WxConstant.MassMsgType.TEXT, sub);
        }
        if (WxConstant.MassMsgType.VOICE.equals(wxMpMassPreviewMessage.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
            jsonObject.add(WxConstant.MassMsgType.VOICE, sub);
        }
        if (WxConstant.MassMsgType.IMAGE.equals(wxMpMassPreviewMessage.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
            jsonObject.add(WxConstant.MassMsgType.IMAGE, sub);
        }
        if (WxConstant.MassMsgType.MPVIDEO.equals(wxMpMassPreviewMessage.getMsgType())) {
            JsonObject sub = new JsonObject();
            sub.addProperty("media_id", wxMpMassPreviewMessage.getMediaId());
            jsonObject.add(WxConstant.MassMsgType.MPVIDEO, sub);
        }
        jsonObject.addProperty("msgtype", wxMpMassPreviewMessage.getMsgType());
        return jsonObject;
    }
}
