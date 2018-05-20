package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.card.request.WxMpCardBaseRequest;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGeneralCouponCardDetail implements Serializable {
    private static final long serialVersionUID = 5568992094294108593L;

    @SerializedName("base_info")
    private WxMpCardBaseInfo baseInfo;

    @SerializedName("advanced_info")
    private WxMpCardAdvancedInfo advancedInfo;

    @SerializedName("default_detail")
    private String defaultDetail;
}