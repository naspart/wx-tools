package com.rolbel.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义会员信息类目.
 */
@Data
public class CustomCell1 implements Serializable {
    private static final long serialVersionUID = 185075815140417294L;

    /**
     * 入口名称.
     */
    @SerializedName("name")
    private String name;

    /**
     * 入口右侧提示语,6个汉字内.
     */
    @SerializedName("tips")
    private String tips;

    /**
     * 入口跳转链接.
     */
    @SerializedName("url")
    private String url;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
