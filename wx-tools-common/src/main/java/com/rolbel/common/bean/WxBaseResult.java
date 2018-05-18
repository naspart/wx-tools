package com.rolbel.common.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WxBaseResult {
    @SerializedName(value = "errcode")
    private Integer errCode;

    @SerializedName(value = "errmsg")
    private String errMsg;
}
