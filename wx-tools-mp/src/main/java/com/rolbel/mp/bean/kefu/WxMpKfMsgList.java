package com.rolbel.mp.bean.kefu;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfMsgList implements Serializable {
    private static final long serialVersionUID = -8927038561663728214L;

    @SerializedName("recordlist")
    private List<WxMpKfMsgRecord> records;

    @SerializedName("number")
    private Integer number;

    @SerializedName("msgid")
    private Long msgId;

    public static WxMpKfMsgList fromJson(String responseContent) {
        return WxMpGsonBuilder.create().fromJson(responseContent, WxMpKfMsgList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
