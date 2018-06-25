package com.rolbel.mp.bean.wx_card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpCardTextImage implements Serializable {
    private static final long serialVersionUID = 2757665813209075390L;

    /**
     * 图片链接，必须调用 上传图片接口 上传图片获得链接，并在此填入， 否则报错
     */
    @SerializedName("image_url")
    private String imageUrl;

    /**
     * 图文描述
     */
    @SerializedName("text")
    private String text;
}