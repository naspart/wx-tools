package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.card.request.WxMpCardBaseRequest;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WxMpCardCreateRequest {
    @SerializedName(value = "card")
    private WxMpCardBaseRequest card;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
