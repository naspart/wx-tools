package com.rolbel.mp.bean.shake_around;

import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundRelationSearchQuery implements Serializable {
    private static final long serialVersionUID = 2868046934284659614L;

    private int type;
    private Integer pageId;
    private Integer begin;
    private Integer count;
    private WxMpShakeAroundDeviceIdentifier deviceIdentifier;

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        switch (type) {
            case 1:
                jsonObject.add("device_identifier", deviceIdentifier.toJsonObject());
                break;
            case 2:
                jsonObject.addProperty("page_id", pageId);
                jsonObject.addProperty("begin", begin);
                jsonObject.addProperty("count", count);
                break;
            default:
                throw new IllegalArgumentException("type error");
        }

        return jsonObject.toString();
    }
}
