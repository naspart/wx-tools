package com.rolbel.ma.bean;

import com.rolbel.ma.bean.qrcode.WxMaQrcodeLineColor;
import com.rolbel.ma.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 小程序码接口B.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaWxcodeLimit extends AbstractWxMaQrcodeWrapper implements Serializable {
    private static final long serialVersionUID = 6396621522942698961L;

    private String scene;
    private String page;

    private int width = 430;

    @SerializedName("auto_color")
    private boolean autoColor = true;

    @SerializedName("line_color")
    private WxMaQrcodeLineColor lineColor = new WxMaQrcodeLineColor("0", "0", "0");

    public static WxMaWxcodeLimit fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaWxcodeLimit.class);
    }
}
