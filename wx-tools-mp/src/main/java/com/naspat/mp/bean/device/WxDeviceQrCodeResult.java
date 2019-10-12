package com.naspat.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceQrCodeResult extends AbstractDeviceBean {
    private static final long serialVersionUID = -3622630858847210199L;

    @SerializedName("deviceid")
    private String deviceId;

    @SerializedName("qrticket")
    private String qrTicket;

    @SerializedName("devicelicence")
    private String deviceLicence;

    @SerializedName("base_resp")
    private BaseResp baseResp;

    public static WxDeviceQrCodeResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxDeviceQrCodeResult.class);
    }
}
