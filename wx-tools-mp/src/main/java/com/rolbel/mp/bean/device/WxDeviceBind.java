package com.rolbel.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceBind extends AbstractDeviceBean {
    private static final long serialVersionUID = 8058566958108097523L;

    private String ticket;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("openid")
    private String openId;
}
