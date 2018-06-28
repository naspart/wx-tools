package com.rolbel.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.json.WxGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransMsgResp extends AbstractDeviceBean {
    private static final long serialVersionUID = -3230969939687962522L;

    private Integer ret;

    @SerializedName("ret_info")
    private String retInfo;

    @SerializedName("errcode")
    private Integer errCode;

    @SerializedName("errmsg")
    private String errMsg;

    public static TransMsgResp fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, TransMsgResp.class);
    }
}
