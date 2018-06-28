package com.rolbel.mp.bean.card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpCardCreateLandingPage implements Serializable {
    private static final long serialVersionUID = -4372683014582332448L;

    @SerializedName(value = "banner")
    private String banner;

    @SerializedName(value = "page_title")
    private String pageTitle;

    @SerializedName(value = "can_share")
    private boolean canShare;

    @SerializedName(value = "scene")
    private String scene;

    @SerializedName(value = "card_list")
    private List<CardInfo> cardList;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }

    @Data
    public static class CardInfo implements Serializable {
        private static final long serialVersionUID = -3277731196053627492L;

        @SerializedName(value = "card_id")
        private String cardId;

        @SerializedName(value = "thumb_url")
        private String thumbUrl;
    }
}
