package com.naspat.mp.bean.device;


import com.naspat.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * 设备抽象类.
 */
public abstract class AbstractDeviceBean implements Serializable {
    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }
}
