package com.naspat.mp.bean;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMassPreviewMessage implements Serializable {
    private static final long serialVersionUID = -4799557136351680732L;

    private String toWxUserName;
    private String toWxUserOpenid;
    /**
     * <pre>
     * 消息类型
     * 请使用
     * {@link com.naspat.common.api.WxConstant.MassMsgType#IMAGE}
     * {@link com.naspat.common.api.WxConstant.MassMsgType#MPNEWS}
     * {@link com.naspat.common.api.WxConstant.MassMsgType#TEXT}
     * {@link com.naspat.common.api.WxConstant.MassMsgType#MPVIDEO}
     * {@link com.naspat.common.api.WxConstant.MassMsgType#VOICE}
     * 如果msgtype和media_id不匹配的话，会返回系统繁忙的错误
     * </pre>
     */
    private String msgType;
    private String content;
    private String mediaId;

    public WxMpMassPreviewMessage() {
        super();
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
