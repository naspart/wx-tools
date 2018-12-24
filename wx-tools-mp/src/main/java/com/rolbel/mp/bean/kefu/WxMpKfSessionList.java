package com.rolbel.mp.bean.kefu;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfSessionList implements Serializable {
    private static final long serialVersionUID = 8919475040852621823L;

    /**
     * 会话列表
     */
    @SerializedName("sessionlist")
    private List<WxMpKfSession> kfSessionList;

    public static WxMpKfSessionList fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json,
                WxMpKfSessionList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
