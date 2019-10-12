package com.naspat.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpCardCreateResult implements Serializable {
    private static final long serialVersionUID = 2117055550031802197L;

    @SerializedName("card_id")
    private String cardId;
    private Integer errcode;
    private String errmsg;

    public boolean isSuccess() {
        return 0 == errcode;
    }

    public static WxMpCardCreateResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpCardCreateResult.class);
    }

    public static WxMpCardCreateResult failure(String errmsg) {
        WxMpCardCreateResult result = new WxMpCardCreateResult();
        result.setErrcode(500);
        result.setErrmsg(errmsg);
        return result;
    }

    public static WxMpCardCreateResult success() {
        WxMpCardCreateResult result = new WxMpCardCreateResult();
        result.setErrcode(0);
        result.setErrmsg("ok");
        return result;
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}

