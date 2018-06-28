package com.rolbel.mp.bean.store;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 门店列表结果类
 * </pre>

 */
@Data
public class WxMpStoreListResult implements Serializable {
    private static final long serialVersionUID = -1008106965498372052L;

    /**
     * 错误码，0为正常
     */
    @SerializedName("errcode")
    private Integer errCode;
    /**
     * 错误信息
     */
    @SerializedName("errmsg")
    private String errMsg;
    /**
     * 门店信息列表
     */
    @SerializedName("business_list")
    private List<WxMpStoreInfo> businessList;
    /**
     * 门店信息总数
     */
    @SerializedName("total_count")
    private Integer totalCount;

    public static WxMpStoreListResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpStoreListResult.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

}
