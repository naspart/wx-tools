package com.rolbel.mp.bean.shake;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeDeviceApplyResult implements Serializable {

    private static final long serialVersionUID = -3426978194996632490L;

    @SerializedName(value = "apply_id")
    private Long applyId;

    @SerializedName(value = "audit_status")
    private Integer auditStatus;

    @SerializedName(value = "audit_comment")
    private String auditComment;
}
