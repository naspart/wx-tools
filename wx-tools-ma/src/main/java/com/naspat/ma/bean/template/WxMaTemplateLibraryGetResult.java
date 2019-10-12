package com.naspat.ma.bean.template;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.naspat.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMaTemplateLibraryGetResult implements Serializable {
    private static final long serialVersionUID = -4592685211700904092L;

    private String id;
    private String title;
    @SerializedName("keyword_list")
    private List<KeywordInfo> keywordList;

    @Data
    public static class KeywordInfo implements Serializable {
        private static final long serialVersionUID = 5148095862706995534L;

        @SerializedName("keyword_id")
        private int keywordId;
        private String name;
        private String example;
    }

    public static WxMaTemplateLibraryGetResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMaTemplateLibraryGetResult.class);
    }
}
