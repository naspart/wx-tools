package com.rolbel.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import com.rolbel.open.bean.WxOpenResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaQueryAuditResult extends WxOpenResult {
    private static final long serialVersionUID = 73510692813449238L;

    /**
     * 审核编号.
     */
    @SerializedName("auditid")
    Long auditId;

    /**
     * 审核状态:2-审核中,0-审核通过,1-审核失败.
     */
    Integer status;

    /**
     * 审核失败原因.
     */
    String reason;
}
