package com.naspat.mp.bean.template;

import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 模板列表信息
 * </pre>
 */
@Data
public class WxMpTemplate implements Serializable {
    private static final long serialVersionUID = -7858312295839131426L;

    private static final JsonParser JSON_PARSER = new JsonParser();

    /**
     * template_id
     * 模板ID
     */
    @SerializedName("template_id")
    private String templateId;
    /**
     * title
     * 模板标题
     */
    @SerializedName("title")
    private String title;
    /**
     * primary_industry
     * 模板所属行业的一级行业
     */
    @SerializedName("primary_industry")
    private String primaryIndustry;
    /**
     * deputy_industry
     * 模板所属行业的二级行业
     */
    @SerializedName("deputy_industry")
    private String deputyIndustry;
    /**
     * content
     * 模板内容
     */
    @SerializedName("content")
    private String content;
    /**
     * example
     * 模板示例
     */
    @SerializedName("example")
    private String example;

    public static List<WxMpTemplate> fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(JSON_PARSER.parse(json).getAsJsonObject().get("template_list"),
                new TypeToken<List<WxMpTemplate>>() {
                }.getType());
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
