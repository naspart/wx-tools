package com.rolbel.mp.bean.kefu;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfOnlineList implements Serializable {
    private static final long serialVersionUID = 9129909012952241467L;

    @SerializedName("kf_online_list")
    private List<WxMpKfInfo> kfOnlineList;

    public static WxMpKfOnlineList fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpKfOnlineList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
