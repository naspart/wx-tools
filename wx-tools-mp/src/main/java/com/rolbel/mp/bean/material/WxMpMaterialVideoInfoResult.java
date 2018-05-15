package com.rolbel.mp.bean.material;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialVideoInfoResult implements Serializable {
    private static final long serialVersionUID = 1269131745333792202L;

    private String title;
    private String description;
    private String downUrl;

    public static WxMpMaterialVideoInfoResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialVideoInfoResult.class);
    }
}
