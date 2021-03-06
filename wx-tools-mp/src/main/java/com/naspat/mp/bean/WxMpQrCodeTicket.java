package com.naspat.mp.bean;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 换取二维码的Ticket
 */
@Data
public class WxMpQrCodeTicket implements Serializable {
    private static final long serialVersionUID = -9149256745095958781L;

    protected String ticket;
    /**
     * 如果为-1，说明是永久
     */
    protected int expireSeconds = -1;
    protected String url;

    public static WxMpQrCodeTicket fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpQrCodeTicket.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
