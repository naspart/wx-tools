package com.naspat.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 刷卡功能.
 */
@Data
public class SwipeCard implements Serializable {

    /**
     * 是否设置该会员卡支持拉出微信支付刷卡界面
     */
    @SerializedName("is_swipe_card")
    private boolean isSwipeCard;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
