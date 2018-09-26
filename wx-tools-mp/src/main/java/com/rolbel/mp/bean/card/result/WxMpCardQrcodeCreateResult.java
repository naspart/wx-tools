package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Data
public class WxMpCardQrcodeCreateResult implements Serializable {
    private static final long serialVersionUID = -4861880015931972431L;

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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}