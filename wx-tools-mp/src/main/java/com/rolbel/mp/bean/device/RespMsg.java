package com.rolbel.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RespMsg extends AbstractDeviceBean {
    private static final long serialVersionUID = -4780047179096604528L;

    @SerializedName("ret_code")
    private Integer retCode;

    @SerializedName("error_info")
    private String errorInfo;
}
