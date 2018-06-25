package com.rolbel.mp.bean.shake_around;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundRegisterQueryResult implements Serializable {

    private static final long serialVersionUID = -1187267899860330620L;

    @SerializedName(value = "apply_time")
    private Long applyTime;

    @SerializedName(value = "audit_comment")
    private String auditComment;

    @SerializedName(value = "audit_status")
    private Integer auditStatus;

    @SerializedName(value = "audit_time")
    private Long auditTime;


}
