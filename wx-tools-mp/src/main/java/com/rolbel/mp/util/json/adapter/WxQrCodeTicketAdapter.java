package com.rolbel.mp.util.json.adapter;

import com.google.gson.*;
import com.rolbel.common.util.json.GsonHelper;
import com.rolbel.mp.bean.WxMpQrCodeTicket;

import java.lang.reflect.Type;

/**
 * @author Daniel Qian
 */
public class WxQrCodeTicketAdapter implements JsonDeserializer<WxMpQrCodeTicket> {

    @Override
    public WxMpQrCodeTicket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMpQrCodeTicket ticket = new WxMpQrCodeTicket();
        JsonObject ticketJsonObject = json.getAsJsonObject();

        if (ticketJsonObject.get("ticket") != null && !ticketJsonObject.get("ticket").isJsonNull()) {
            ticket.setTicket(GsonHelper.getAsString(ticketJsonObject.get("ticket")));
        }
        if (ticketJsonObject.get("expire_seconds") != null && !ticketJsonObject.get("expire_seconds").isJsonNull()) {
            ticket.setExpireSeconds(GsonHelper.getAsPrimitiveInt(ticketJsonObject.get("expire_seconds")));
        }
        if (ticketJsonObject.get("url") != null && !ticketJsonObject.get("url").isJsonNull()) {
            ticket.setUrl(GsonHelper.getAsString(ticketJsonObject.get("url")));
        }
        return ticket;
    }
}
