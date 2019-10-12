package com.naspat.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceBindResult extends AbstractDeviceBean {
    private static final long serialVersionUID = -4693325900693488085L;

    @SerializedName("base_resp")
    private BaseResp baseResp;

    public static WxDeviceBindResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxDeviceBindResult.class);
    }
}
