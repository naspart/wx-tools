package com.rolbel.ma.bean.code;

import com.google.gson.annotations.SerializedName;
import com.rolbel.ma.util.json.WxMaGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 提交审核的请求
 */
@Data
@Builder
public class WxMaCodeSubmitAuditRequest implements Serializable {
    private static final long serialVersionUID = -6030562570535129928L;

    /**
     * 提交审核项的一个列表（至少填写1项，至多填写5项）
     */
    @SerializedName("item_list")
    private List<WxMaCategory> itemList;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
