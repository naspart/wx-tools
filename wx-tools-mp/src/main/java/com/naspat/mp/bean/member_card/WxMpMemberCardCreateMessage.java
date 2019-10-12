package com.naspat.mp.bean.member_card;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.bean.card.MemberCardCreateRequest;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public final class WxMpMemberCardCreateMessage implements Serializable {
    private static final long serialVersionUID = -864203957016043757L;

    @SerializedName("card")
    private MemberCardCreateRequest cardCreateRequest;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static WxMpMemberCardCreateMessage fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMemberCardCreateMessage.class);
    }
}
