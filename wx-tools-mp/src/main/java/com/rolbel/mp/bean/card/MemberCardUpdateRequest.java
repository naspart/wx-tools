package com.rolbel.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberCardUpdateRequest implements Serializable {
    @SerializedName("card_id")
    private String cardId;

    @SerializedName("member_card")
    private MemberCardUpdate memberCardUpdate;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
