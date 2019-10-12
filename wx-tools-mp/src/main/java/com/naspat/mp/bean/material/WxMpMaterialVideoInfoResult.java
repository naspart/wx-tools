package com.naspat.mp.bean.material;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialVideoInfoResult implements Serializable {
    private static final long serialVersionUID = -7248793143909439903L;

    private String title;
    private String description;
    private String downUrl;

    public static WxMpMaterialVideoInfoResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialVideoInfoResult.class);
    }
}
