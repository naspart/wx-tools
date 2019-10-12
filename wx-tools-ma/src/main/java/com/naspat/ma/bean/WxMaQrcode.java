package com.naspat.ma.bean;

import com.naspat.ma.util.json.WxMaGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaQrcode extends AbstractWxMaQrcodeWrapper implements Serializable {
    private static final long serialVersionUID = 7905288490530414267L;

    private String path;
    private int width = 430;

    public WxMaQrcode(String path, int width) {
        this.path = path;
        this.width = width;
    }

    public static WxMaQrcode fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaQrcode.class);
    }

    @Override
    public String toString() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
