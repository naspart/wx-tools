package com.naspat.mp.bean.shake_around;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundRegisterQueryResult implements Serializable {
    private static final long serialVersionUID = -931421913092732544L;

    @SerializedName(value = "apply_time")
    private Long applyTime;

    @SerializedName(value = "audit_comment")
    private String auditComment;

    @SerializedName(value = "audit_status")
    private Integer auditStatus;

    @SerializedName(value = "audit_time")
    private Long auditTime;
}
