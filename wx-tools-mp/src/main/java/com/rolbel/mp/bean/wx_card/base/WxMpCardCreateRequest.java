package com.rolbel.mp.bean.wx_card.base;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.wx_card.request.WxMpCard;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpCardCreateRequest implements Serializable {
    private static final long serialVersionUID = -5753441337512689308L;

    @SerializedName(value = "wx_card")
    private WxMpCard card;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
