package com.rolbel.ma.bean.template;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.rolbel.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMaTemplateLibraryListResult implements Serializable {
    private static final long serialVersionUID = -679028108953283855L;

    @SerializedName("total_count")
    private int totalCount;

    private List<TemplateItem> list;

    public static WxMaTemplateLibraryListResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMaTemplateLibraryListResult.class);
    }

    @Data
    public static class TemplateItem implements Serializable {
        private static final long serialVersionUID = -6655912396517433818L;

        private String id;
        private String title;
    }
}
