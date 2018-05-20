package com.rolbel.miniapp.bean;

import com.google.gson.annotations.SerializedName;
import com.rolbel.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * {"session_key":"nzoqhc3OnwHzeTxJs+inbQ==","expires_in":2592000,"openid":"oVBkZ0aYgDMDIywRdgPW8-joxXc4"}
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaJscode2SessionResult implements Serializable {
    private static final long serialVersionUID = -1060216618475607933L;

    @SerializedName("session_key")
    private String sessionKey;

    @SerializedName("expires_in")
    private Integer expiresin;

    @SerializedName("openid")
    private String openid;

    @SerializedName("unionid")
    private String unionid;

    public static WxMaJscode2SessionResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaJscode2SessionResult.class);
    }
}
