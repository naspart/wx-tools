package com.naspat.common.bean;

import com.naspat.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡券Api签名.
 */
@Data
public class WxCardApiSignature implements Serializable {
    private static final long serialVersionUID = -5411723255156528820L;

    private String appId;

    private String cardId;

    private String cardType;

    private String locationId;

    private String code;

    private String openId;

    private Long timestamp;

    private String nonceStr;

    private String signature;

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }
}
