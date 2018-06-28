package com.rolbel.mp.bean.shake_around;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class WxMpShakeAroundDeviceBindPageQuery implements Serializable {
    private static final long serialVersionUID = 4171971033248730427L;

    private WxMpShakeAroundDeviceIdentifier deviceIdentifier;
    private Collection<Integer> pageIds;

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("device_identifier", deviceIdentifier.toJsonObject());
        JsonArray jsonArray = new JsonArray();
        for (Integer pageid : pageIds) {
            jsonArray.add(pageid);
        }
        jsonObject.add("page_ids", jsonArray);

        return jsonObject.toString();
    }
}
