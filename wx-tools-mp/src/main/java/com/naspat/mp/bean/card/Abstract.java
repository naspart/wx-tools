package com.naspat.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 封面摘要.
 */
@Data
public class Abstract implements Serializable {
    private static final long serialVersionUID = -2117255448498600306L;

    /**
     * 摘要.
     */
    @SerializedName("abstract")
    private String abstractInfo;

    /**
     * 封面图片列表.
     * 仅支持填入一 个封面图片链接， 上传图片接口 上传获取图片获得链接，填写 非CDN链接会报错，并在此填入。 建议图片尺寸像素850*350
     */
    @SerializedName("icon_url_list")
    private String iconUrlList;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
