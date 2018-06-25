package com.rolbel.mp.bean.wx_card.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpCreateCardResult implements Serializable {
    private static final long serialVersionUID = 9200314196853749072L;

    @SerializedName(value = "card_id")
    private String cardId;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
