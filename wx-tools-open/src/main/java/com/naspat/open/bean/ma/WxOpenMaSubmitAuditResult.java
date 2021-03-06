package com.naspat.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import com.naspat.open.bean.WxOpenResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信开放平台小程序发布代码审核结果.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaSubmitAuditResult extends WxOpenResult {
    private static final long serialVersionUID = 7431725910039734365L;

    /**
     * 审核编号.
     */
    @SerializedName("auditid")
    Long auditId;
}
