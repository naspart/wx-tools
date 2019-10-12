package com.naspat.mp.bean.member_card;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 拉取会员信息返回的结果
 *
 * 字段格式参考https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025283  6.2.1小节的步骤5
 * </pre>
 */
@Data
public class WxMpMemberCardUserInfoResult implements Serializable {
    private static final long serialVersionUID = 3071964029224827676L;

    private String errorCode;

    private String errorMsg;

    private String openId;

    private String nickname;

    private String membershipNumber;

    private Integer bonus;

    private Double balance;

    private String sex;

    private WxMpMemberCardUserInfo userInfo;

    private String userCardStatus;

    private Boolean hasActive;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static WxMpMemberCardUserInfoResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMemberCardUserInfoResult.class);
    }
}

