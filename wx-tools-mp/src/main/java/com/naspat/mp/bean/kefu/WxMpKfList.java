package com.naspat.mp.bean.kefu;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfList implements Serializable {
    private static final long serialVersionUID = -5991047010925377122L;

    @SerializedName("kf_list")
    private List<WxMpKfInfo> kfList;

    public static WxMpKfList fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpKfList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
