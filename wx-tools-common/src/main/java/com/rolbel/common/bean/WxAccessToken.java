package com.rolbel.common.bean;

import com.rolbel.common.util.json.WxGsonBuilder;
import lombok.Data;

@Data
public class WxAccessToken {
    private String accessToken;
    private int expiresIn = -1;

    public static WxAccessToken fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
    }
}
