package com.rolbel.ma.bean;

import com.rolbel.ma.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMaShareInfo implements Serializable {
    private static final long serialVersionUID = -2775468942232540070L;

    private String openGId;

    public static WxMaShareInfo fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaShareInfo.class);
    }
}
