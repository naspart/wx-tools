package com.rolbel.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfOnlineList implements Serializable {
    private static final long serialVersionUID = -5318774739467087299L;

    @SerializedName("kf_online_list")
    private List<WxMpKfInfo> kfOnlineList;

    public static WxMpKfOnlineList fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfOnlineList.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
