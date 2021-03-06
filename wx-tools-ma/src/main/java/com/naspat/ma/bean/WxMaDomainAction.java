package com.naspat.ma.bean;

import com.google.gson.annotations.SerializedName;
import com.naspat.ma.util.json.WxMaGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 域名相关操作
 */
@Data
@Builder
public class WxMaDomainAction implements Serializable {
    private static final long serialVersionUID = -5049908822391008340L;

    /**
     * add添加, delete删除, set覆盖, get获取。当参数是get时不需要填四个域名字段
     */
    private String action;
    /**
     * request合法域名，当action参数是get时不需要此字段。
     */
    @SerializedName("requestdomain")
    private List<String> requestDomain;
    /**
     * socket合法域名，当action参数是get时不需要此字段。
     */
    @SerializedName("wsrequestdomain")
    private List<String> wsRequestDomain;
    /**
     * uploadFile合法域名，当action参数是get时不需要此字段。
     */
    @SerializedName("uploaddomain")
    private List<String> uploadDomain;
    /**
     * downloadFile合法域名，当action参数是get时不需要此字段。
     */
    @SerializedName("downloaddomain")
    private List<String> downloadDomain;
    /**
     * 小程序业务域名，当action参数是get时不需要此字段。
     */
    @SerializedName("webviewdomain")
    private List<String> webViewDomain;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }

    public static WxMaDomainAction fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaDomainAction.class);
    }
}
