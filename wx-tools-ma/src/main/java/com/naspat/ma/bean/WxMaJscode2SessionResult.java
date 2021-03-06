package com.naspat.ma.bean;

import com.naspat.ma.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <pre>
 * code换取session_key接口的响应
 * 文档地址：https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-login.html#wxloginobject
 * 微信返回报文：{"session_key":"nzoqhc3OnwHzeTxJs+inbQ==","openid":"oVBkZ0aYgDMDIywRdgPW8-joxXc4"}
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaJscode2SessionResult implements Serializable {
    private static final long serialVersionUID = 6140768758715619547L;

    @SerializedName("session_key")
    private String sessionKey;

    @SerializedName("openid")
    private String openId;

    @SerializedName("unionid")
    private String unionId;

    public static WxMaJscode2SessionResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaJscode2SessionResult.class);
    }

}
