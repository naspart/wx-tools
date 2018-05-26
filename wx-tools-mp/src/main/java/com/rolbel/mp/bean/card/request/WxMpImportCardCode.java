package com.rolbel.mp.bean.card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpImportCardCode implements Serializable {
    private static final long serialVersionUID = -4621334062349943093L;

    @SerializedName(value = "card_id")
    private String cardId;

    @SerializedName(value = "code")
    private List<String> code;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
