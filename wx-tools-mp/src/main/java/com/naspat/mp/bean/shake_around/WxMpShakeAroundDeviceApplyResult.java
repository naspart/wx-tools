package com.naspat.mp.bean.shake_around;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundDeviceApplyResult implements Serializable {
    private static final long serialVersionUID = 1555647572710073375L;

    @SerializedName(value = "apply_id")
    private Long applyId;

    @SerializedName(value = "audit_status")
    private Integer auditStatus;

    @SerializedName(value = "audit_comment")
    private String auditComment;
}
