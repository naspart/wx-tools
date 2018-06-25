package com.rolbel.mp.util.json.adapter;

import com.google.gson.*;
import com.rolbel.common.api.WxConstant;
import com.rolbel.mp.bean.WxMpMassOpenIdsMessage;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

public class WxMpMassOpenIdsMessageGsonAdapter implements JsonSerializer<WxMpMassOpenIdsMessage> {

    @Override
    public JsonElement serialize(WxMpMassOpenIdsMessage message, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject messageJson = new JsonObject();

        JsonArray toUsers = new JsonArray();
        for (String openId : message.getToUsers()) {
            toUsers.add(new JsonPrimitive(openId));
        }
        messageJson.add("touser", toUsers);

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

        if (StringUtils.isNotEmpty(message.getClientMsgId())) {
            messageJson.addProperty("clientmsgid", message.getClientMsgId());
        }

        return messageJson;
    }
}
