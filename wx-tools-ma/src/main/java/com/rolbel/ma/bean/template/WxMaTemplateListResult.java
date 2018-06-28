package com.rolbel.ma.bean.template;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.rolbel.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMaTemplateListResult implements Serializable {
    private static final long serialVersionUID = -5613791713446300917L;

    private List<TemplateInfo> list;

    public static WxMaTemplateListResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMaTemplateListResult.class);
    }

    @Data
    public static class TemplateInfo implements Serializable {
        private static final long serialVersionUID = -1519050909356992608L;

        @SerializedName("template_id")
        private String templateId;
        private String title;
        private String content;
        private String example;
    }
}
