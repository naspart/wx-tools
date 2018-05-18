package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpGrouponCardDetail implements Serializable {
    private static final long serialVersionUID = -1103433073416775068L;

    @SerializedName("base_info")
    private WxMpCardBaseInfo baseInfo;

    @SerializedName("advanced_info")
    private WxMpCardAdvancedInfo advancedInfo;

    @SerializedName("deal_detail")
    private String dealDetail;
}
