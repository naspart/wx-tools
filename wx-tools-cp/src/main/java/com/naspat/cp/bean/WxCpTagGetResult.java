package com.naspat.cp.bean;

import com.google.gson.annotations.SerializedName;
import com.naspat.cp.util.json.WxCpGsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  管理企业号应用-测试
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxCpTagGetResult implements Serializable {
    private static final long serialVersionUID = 8860919890992563733L;

    @SerializedName("errcode")
    private Integer errcode;

    @SerializedName("errmsg")
    private String errmsg;

    /**
     * 用户列表
     */
    @SerializedName("userlist")
    private List<WxCpUser> userlist;

    /**
     * 部门列表
     */
    @SerializedName("partylist")
    private List<Integer> partylist;

    /**
     * 标签名称
     */
    @SerializedName("tagname")
    private String tagname;

    public static WxCpTagGetResult fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpTagGetResult.class);
    }

    public String toJson() {
        return WxCpGsonBuilder.create().toJson(this);
    }

}
