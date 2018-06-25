package com.rolbel.mp.bean.wx_card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGeneralCouponCardDetail implements Serializable {
    private static final long serialVersionUID = 14069120053169351L;

    @SerializedName("base_info")
    private WxMpCardBaseInfo baseInfo;

    @SerializedName("advanced_info")
    private WxMpCardAdvancedInfo advancedInfo;

    @SerializedName("default_detail")
    private String defaultDetail;
}
