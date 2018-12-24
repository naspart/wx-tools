package com.rolbel.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpCardQrcodeCreateResult implements Serializable {
    private static final long serialVersionUID = 2453488086547094195L;

    private Integer errcode;
    private String errmsg;
    private String ticket;

    @SerializedName("expire_seconds")
    private Integer expireSeconds;

    private String url;

    @SerializedName("show_qrcode_url")
    private String showQrcodeUrl;

    public boolean isSuccess() {
        return 0 == errcode;
    }

    public static WxMpCardQrcodeCreateResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpCardQrcodeCreateResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}

