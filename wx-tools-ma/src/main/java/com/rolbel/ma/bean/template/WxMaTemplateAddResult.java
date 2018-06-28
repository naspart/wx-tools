package com.rolbel.ma.bean.template;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.rolbel.common.util.json.WxGsonBuilder;

import java.io.Serializable;

@Data
public class WxMaTemplateAddResult implements Serializable {
    private static final long serialVersionUID = -1818404922176075473L;

    @SerializedName("template_id")
    private String templateId;

    public static WxMaTemplateAddResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMaTemplateAddResult.class);
    }
}
