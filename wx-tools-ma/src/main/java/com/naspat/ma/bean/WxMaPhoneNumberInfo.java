package com.naspat.ma.bean;

import com.naspat.ma.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信用户绑定的手机号相关信息
 */
@Data
public class WxMaPhoneNumberInfo implements Serializable {
    private static final long serialVersionUID = 6842599917631916716L;

    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Watermark watermark;

    public static WxMaPhoneNumberInfo fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaPhoneNumberInfo.class);
    }

    @Data
    public static class Watermark {
        private String timestamp;
        private String appid;
    }
}
