package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpCardCreateCardResult implements Serializable {
    private static final long serialVersionUID = -2392742543657208575L;

    @SerializedName(value = "card_id")
    private String cardId;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
