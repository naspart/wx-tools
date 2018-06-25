package com.rolbel.mp.bean.wx_card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.wx_card.base.WxMpGiftCardDetail;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGiftCard implements WxMpCard, Serializable {
    private static final long serialVersionUID = 8924757672228865485L;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("gift")
    private WxMpGiftCardDetail gift;

    @Override
    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
