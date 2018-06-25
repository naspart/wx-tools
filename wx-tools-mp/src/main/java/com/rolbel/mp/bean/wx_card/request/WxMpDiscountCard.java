package com.rolbel.mp.bean.wx_card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.wx_card.base.WxMpDiscountCardDetail;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpDiscountCard implements WxMpCard, Serializable {
    private static final long serialVersionUID = -1963946477984540063L;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("discount")
    private WxMpDiscountCardDetail discount;

    @Override
    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
