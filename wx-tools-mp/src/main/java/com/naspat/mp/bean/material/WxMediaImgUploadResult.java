package com.naspat.mp.bean.material;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMediaImgUploadResult implements Serializable {
    private static final long serialVersionUID = 3301119355459796141L;

    private String url;

    public static WxMediaImgUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMediaImgUploadResult.class);
    }
}
