package com.naspat.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 微信小程序代码包提交审核(仅供第三方开发者代小程序调用)
 */
@Data
public class WxOpenMaSubmitAuditMessage implements Serializable {

    @SerializedName("item_list")
    private List<WxOpenMaSubmitAudit> itemList;
}
