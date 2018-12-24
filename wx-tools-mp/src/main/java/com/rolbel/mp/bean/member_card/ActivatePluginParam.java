package com.rolbel.mp.bean.member_card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ActivatePluginParam {

    @SerializedName("encrypt_card_id")
    String encryptCardId;

    @SerializedName("outer_str")
    String outerStr;

    @SerializedName("biz")
    String biz;

}
