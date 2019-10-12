package com.naspat.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberCardCreateRequest implements Serializable {
    @SerializedName("card_type")
    private String cardType = "MEMBER_CARD";

    @SerializedName("member_card")
    private MemberCard memberCard;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
