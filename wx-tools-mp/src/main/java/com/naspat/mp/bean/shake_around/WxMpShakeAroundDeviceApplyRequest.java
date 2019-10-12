package com.naspat.mp.bean.shake_around;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundDeviceApplyRequest implements Serializable {
    private static final long serialVersionUID = 31993142554133673L;

    @SerializedName(value = "quantity")
    private Integer quantity;

    @SerializedName(value = "apply_reason")
    private String applyReason;

    @SerializedName(value = "comment")
    private String comment;

    @SerializedName(value = "poi_id")
    private Long poiId;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
