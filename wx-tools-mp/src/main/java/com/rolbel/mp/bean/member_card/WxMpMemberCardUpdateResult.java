package com.rolbel.mp.bean.member_card;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 用于 `7 更新会员信息` 的接口调用后的返回结果
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025283
 * </pre>
 */
@Data
public class WxMpMemberCardUpdateResult implements Serializable {
    private static final long serialVersionUID = 3915428471134678866L;

    private String errorCode;

    private String errorMsg;

    private String openId;

    private Integer resultBonus;

    private Double resultBalance;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public static WxMpMemberCardUpdateResult fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpMemberCardUpdateResult.class);
    }
}
