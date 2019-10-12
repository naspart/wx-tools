package com.naspat.ma.bean;

import com.naspat.ma.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMaUserInfo implements Serializable {
    private static final long serialVersionUID = 6856380766400612902L;

    private String openId;
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private Watermark watermark;

    public static WxMaUserInfo fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaUserInfo.class);
    }

    @Data
    public static class Watermark implements Serializable {
        private static final long serialVersionUID = 5266390388952753356L;

        private String timestamp;
        private String appId;
    }
}
