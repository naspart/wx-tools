package com.rolbel.mp.bean.wx_card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGiftCardDetail implements Serializable {
    private static final long serialVersionUID = 6840644032882354224L;

    @SerializedName("base_info")
    private WxMpCardBaseInfo baseInfo;

    @SerializedName("advanced_info")
    private WxMpCardAdvancedInfo advancedInfo;

    @SerializedName("gift")
    private String gift;
}
