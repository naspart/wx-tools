package com.rolbel.mp.bean.device;


import com.rolbel.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * 设备抽象类.
 */
public abstract class AbstractDeviceBean implements Serializable {
    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }
}
