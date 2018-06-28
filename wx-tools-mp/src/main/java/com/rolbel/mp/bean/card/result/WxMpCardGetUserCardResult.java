package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpCardGetUserCardResult implements Serializable {
    private static final long serialVersionUID = -907891657106802108L;

    @SerializedName(value = "has_share_card")
    private boolean hasShareCard;

    @SerializedName(value = "card_list")
    private List<CardInfo> cardList;

    @Data
    public static class CardInfo implements Serializable {
        private static final long serialVersionUID = -7516484025597109592L;

        @SerializedName(value = "card_id")
        private String cardId;

        @SerializedName(value = "code")
        private String code;
    }
}
