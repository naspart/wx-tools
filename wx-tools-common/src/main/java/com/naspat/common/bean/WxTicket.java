package com.naspat.common.bean;

import com.naspat.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxTicket implements Serializable {
    private static final long serialVersionUID = -1148454743386499558L;

    private String ticket;
    private int expiresIn = -1;

    public static WxTicket fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxTicket.class);
    }
}
