package com.rolbel.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * <pre>
 *  使用user_ticket获取成员详情接口返回类.
 * </pre>
 */
@Data
public class WxCpUserDetail {
    @SerializedName("userid")
    private String userId;

    private String name;

    private String mobile;

    private String gender;

    private String email;

    @SerializedName("qrCode")
    private String qrCode;
}
