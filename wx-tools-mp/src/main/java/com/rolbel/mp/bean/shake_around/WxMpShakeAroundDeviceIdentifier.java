package com.rolbel.mp.bean.shake_around;

import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundDeviceIdentifier implements Serializable {
    private static final long serialVersionUID = -1913501758497273484L;

    private Integer device_id;
    private String uuid;
    private Integer page_id;
    private Integer major;
    private Integer minor;

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("device_id", device_id);
        jsonObject.addProperty("uuid", uuid);
        jsonObject.addProperty("major", major);
        jsonObject.addProperty("minor", minor);
        return jsonObject;
    }
}
