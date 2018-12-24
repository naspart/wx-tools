package com.rolbel.ma.bean.code;

import com.google.gson.annotations.SerializedName;
import com.rolbel.ma.util.json.WxMaGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序代码审核状态
 */
@Data
@Builder
public class WxMaCodeAuditStatus implements Serializable {
    private static final long serialVersionUID = 4317809099327932628L;

    /**
     * 审核 ID
     */
    @SerializedName(value = "auditId", alternate = {"auditid"})
    private Long auditId;
    /**
     * 审核状态，其中0为审核成功，1为审核失败，2为审核中
     */
    private Integer status;
    /**
     * 当status=1，审核被拒绝时，返回的拒绝原因
     */
    private String reason;

    public static WxMaCodeAuditStatus fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaCodeAuditStatus.class);
    }
}
