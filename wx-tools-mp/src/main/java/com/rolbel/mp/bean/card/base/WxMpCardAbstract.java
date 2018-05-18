package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WxMpCardAbstract {
    private static final long serialVersionUID = -3410325000294773725L;

    /**
     * 封面摘要简介。
     */
    @SerializedName("abstract")
    private String abstractDesc;

    /**
     * 封面图片列表，仅支持填入一 个封面图片链接， 上传图片接口 上传获取图片获得链接，填写非CDN链接会报错，并在此填入。 建议图片尺寸像素850*350
     */
    @SerializedName("icon_url_list")
    private List<String> iconUrlList;
}
