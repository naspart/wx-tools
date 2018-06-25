package com.rolbel.mp.bean.store;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtil;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 门店列表结果类
 * Created by Binary Wang on 2016-09-27.
 * </pre>

 */
@Data
public class WxMpStoreListResult implements Serializable {
    private static final long serialVersionUID = 5388907559949538663L;

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
        return ToStringUtil.toSimpleString(this);
    }

}
