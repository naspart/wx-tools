package com.rolbel.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpKfSessionGetResult implements Serializable {
    private static final long serialVersionUID = 4441239214325808647L;

    /**
     * kf_account 正在接待的客服，为空表示没有人在接待
     */
    @SerializedName("kf_account")
    private String kfAccount;

    /**
     * createtime 会话接入的时间 ，UNIX时间戳
     */
    @SerializedName("createtime")
    private long createTime;

    public static WxMpKfSessionGetResult fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpKfSessionGetResult.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
