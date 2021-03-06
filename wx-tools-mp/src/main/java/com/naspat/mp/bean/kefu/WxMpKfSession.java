package com.naspat.mp.bean.kefu;

import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpKfSession implements Serializable {
    private static final long serialVersionUID = 7255862793217773570L;
    
    /**
     * kf_account 正在接待的客服，为空表示没有人在接待
     */
    @SerializedName("kf_account")
    private String kfAccount;

    /**
     * createtime 会话接入的时间，UNIX时间戳
     * 该返回值 存在于 获取客服会话列表接口
     */
    @SerializedName("createtime")
    private long createTime;

    /**
     * latest_time 粉丝的最后一条消息的时间，UNIX时间戳
     * 该返回值 存在于 获取未接入会话列表接口
     */
    @SerializedName("latest_time")
    private long latestTime;

    /**
     * openid 客户openid
     */
    @SerializedName("openid")
    private String openid;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
