package com.naspat.mp.bean.store;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpStoreInfo implements Serializable {
    private static final long serialVersionUID = 4374180254887280741L;

    @SerializedName("base_info")
    private WxMpStoreBaseInfo baseInfo;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
