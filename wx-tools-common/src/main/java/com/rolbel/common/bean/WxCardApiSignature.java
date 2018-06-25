package com.rolbel.common.bean;

import com.rolbel.common.util.ToStringUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡券Api签名.
 */
@Data
public class WxCardApiSignature implements Serializable {
    private static final long serialVersionUID = 158176707226975979L;

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
        return ToStringUtil.toSimpleString(this);
    }

}
