package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpCreateLandingPageResult implements Serializable {
    private static final long serialVersionUID = 1996084688506383553L;

    @SerializedName(value = "url")
    private String url;

    @SerializedName(value = "page_id")
    private Integer pageId;
}
