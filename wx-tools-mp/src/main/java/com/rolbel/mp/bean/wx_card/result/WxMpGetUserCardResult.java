package com.rolbel.mp.bean.wx_card.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpGetUserCardResult implements Serializable {
    private static final long serialVersionUID = 388952601107900004L;

    @SerializedName(value = "has_share_card")
    private boolean hasShareCard;

    @SerializedName(value = "card_list")
    private List<CardInfo> cardList;

    @Data
    public static class CardInfo implements Serializable {
        private static final long serialVersionUID = -2842291988956899616L;

        @SerializedName(value = "card_id")
        private String cardId;

        @SerializedName(value = "code")
        private String code;
    }
}
