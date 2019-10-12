package com.naspat.mp.bean.menu;

import com.google.gson.annotations.SerializedName;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpGetSelfMenuInfoResult implements Serializable {
    private static final long serialVersionUID = -6952637585152528604L;

    @SerializedName("selfmenu_info")
    private WxMpSelfMenuInfo selfMenuInfo;

    @SerializedName("is_menu_open")
    private Integer isMenuOpen;

    public static WxMpGetSelfMenuInfoResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMpGetSelfMenuInfoResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
