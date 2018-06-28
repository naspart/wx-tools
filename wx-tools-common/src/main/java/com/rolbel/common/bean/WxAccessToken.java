package com.rolbel.common.bean;

import com.rolbel.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxAccessToken implements Serializable {
    private static final long serialVersionUID = 8295631536165964336L;

    private String accessToken;
    private int expiresIn = -1;

    public static WxAccessToken fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
    }
}
