package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpCashCardDetail implements Serializable {
    private static final long serialVersionUID = 6577566494689481753L;

    @SerializedName("base_info")
    private WxMpCardBaseInfo baseInfo;

    @SerializedName("advanced_info")
    private WxMpCardAdvancedInfo advancedInfo;

    @SerializedName("least_cost")
    private Integer leastCost;

    @SerializedName("reduce_cost")
    private Integer reduceCost;
}
