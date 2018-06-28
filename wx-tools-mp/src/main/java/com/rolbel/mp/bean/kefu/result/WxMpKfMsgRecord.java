package com.rolbel.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpKfMsgRecord implements Serializable {
    private static final long serialVersionUID = 5526853871433698661L;

    /**
     * worker	完整客服帐号，格式为：帐号前缀@公众号微信号
     */
    @SerializedName("worker")
    private String worker;

    /**
     * openid	用户标识
     */
    @SerializedName("openid")
    private String openid;

    /**
     * opercode	操作码，2002（客服发送信息），2003（客服接收消息）
     */
    @SerializedName("opercode")
    private Integer operateCode;

    /**
     * text	聊天记录
     */
    @SerializedName("text")
    private String text;

    /**
     * time	操作时间，unix时间戳
     */
    @SerializedName("time")
    private Long time;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
