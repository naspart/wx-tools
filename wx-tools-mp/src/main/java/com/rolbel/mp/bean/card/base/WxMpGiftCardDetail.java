package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGiftCardDetail implements Serializable {
    private static final long serialVersionUID = -3208987979919577683L;

    @SerializedName("base_info")
    private WxMpCardBaseInfo baseInfo;

    @SerializedName("advanced_info")
    private WxMpCardAdvancedInfo advancedInfo;

    @SerializedName("gift")
    private String gift;
}
