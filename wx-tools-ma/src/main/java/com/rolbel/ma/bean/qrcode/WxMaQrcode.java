package com.rolbel.ma.bean.qrcode;

import com.google.gson.annotations.SerializedName;
import com.rolbel.ma.bean.AbstractWxMaQrcodeWrapper;
import com.rolbel.ma.util.json.WxMaGsonBuilder;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaQrcode extends AbstractWxMaQrcodeWrapper implements Serializable {
    private static final long serialVersionUID = -8404740569957471965L;

    private String path;
    private int width = 430;

    @SerializedName("auto_color")
    private boolean autoColor = true;

    @SerializedName("is_hyaline")
    @Builder.Default
    private boolean isHyaline = false;

    @SerializedName("line_color")
    private WxMaQrcodeLineColor lineColor = new WxMaQrcodeLineColor("0", "0", "0");

    public static WxMaQrcode fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaQrcode.class);
    }
}
