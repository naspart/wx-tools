package com.naspat.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 支付功能.
 */
@Data
public class PayInfo implements Serializable {

    /**
     * 刷卡功能
     */
    @SerializedName("swipe_card")
    private SwipeCard swipeCard;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
