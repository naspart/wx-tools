package com.rolbel.mp.bean.card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.card.base.WxMpGeneralCouponCardDetail;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGeneralCouponCard implements WxMpCardBaseRequest, Serializable {
    private static final long serialVersionUID = -6264816223451466867L;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("general_coupon")
    private WxMpGeneralCouponCardDetail generalCoupon;

    @Override
    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
