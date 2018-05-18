package com.rolbel.mp.bean.card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.card.base.WxMpCashCardDetail;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpCashCard implements WxMpCardBaseRequest, Serializable {
    private static final long serialVersionUID = -2680930413898806701L;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("cash")
    private WxMpCashCardDetail cash;

    @Override
    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
