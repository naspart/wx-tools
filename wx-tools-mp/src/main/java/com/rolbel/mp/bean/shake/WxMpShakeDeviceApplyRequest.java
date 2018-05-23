package com.rolbel.mp.bean.shake;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeDeviceApplyRequest implements Serializable {
    private static final long serialVersionUID = -131930743086311538L;

    @SerializedName(value = "quantity")
    private Integer quantity;

    @SerializedName(value = "apply_reason")
    private String applyReason;

    @SerializedName(value = "comment")
    private String comment;

    @SerializedName(value = "poi_id")
    private Long poiId;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
