package com.rolbel.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtil;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfList implements Serializable {
    private static final long serialVersionUID = -8194193505173564894L;

    @SerializedName("kf_list")
    private List<WxMpKfInfo> kfList;

    public static WxMpKfList fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfList.class);
    }

    @Override
    public String toString() {
        return ToStringUtil.toSimpleString(this);
    }
}
