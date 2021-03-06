package com.naspat.open.bean;

import com.naspat.open.util.json.WxOpenGsonBuilder;

import java.io.Serializable;

public class WxOpenAuthorizerAccessToken implements Serializable {
    private static final long serialVersionUID = 133931268879475076L;

    private String authorizerAccessToken;

    private int expiresIn = -1;

    public static WxOpenAuthorizerAccessToken fromJson(String json) {
        return WxOpenGsonBuilder.create().fromJson(json, WxOpenAuthorizerAccessToken.class);
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
