package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.bean.WxBaseResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WxMpCreateCardResult extends WxBaseResult {
    @SerializedName(value = "card_id")
    private String cardId;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
