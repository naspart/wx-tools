package com.naspat.ma.bean.qrcode;

import com.google.gson.annotations.SerializedName;
import com.naspat.ma.bean.AbstractWxMaQrcodeWrapper;
import com.naspat.ma.util.json.WxMaGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 小程序码接口B.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaQrcodeUnlimit extends AbstractWxMaQrcodeWrapper implements Serializable {
    private static final long serialVersionUID = -6701110558863184478L;

    private String scene;
    private String page;

    private int width = 430;

    @SerializedName("auto_color")
    private boolean autoColor = true;

    @SerializedName("is_hyaline")
    private boolean isHyaline = false;

    @SerializedName("line_color")
    private WxMaQrcodeLineColor lineColor = new WxMaQrcodeLineColor("0", "0", "0");

    public static WxMaQrcodeUnlimit fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaQrcodeUnlimit.class);
    }
}
