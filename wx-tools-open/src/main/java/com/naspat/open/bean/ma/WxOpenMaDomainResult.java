package com.naspat.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import com.naspat.open.bean.WxOpenResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 微信开放平台小程序域名设置返回对象.
 *
 * @author yqx
 * @date 2018/9/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaDomainResult extends WxOpenResult {
    private static final long serialVersionUID = 7080215861794738071L;

    @SerializedName("requestdomain")
    List<String> requestdomainList;

    @SerializedName("wsrequestdomain")
    List<String> wsrequestdomainList;

    @SerializedName("uploaddomain")
    List<String> uploaddomainList;

    @SerializedName("downloaddomain")
    List<String> downloaddomainList;
}
