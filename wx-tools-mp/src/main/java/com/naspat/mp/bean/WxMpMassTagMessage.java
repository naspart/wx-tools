package com.naspat.mp.bean;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 按标签群发的消息
 *
 * @author chanjarster
 */
@Data
public class WxMpMassTagMessage implements Serializable {
    private static final long serialVersionUID = -143801651558185480L;

    /**
     * 标签id，如果不设置则就意味着发给所有用户
     */
    private Long tagId;
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
    /**
     * 是否群发给所有用户
     */
    private boolean isSendAll = false;
    /**
     * 文章被判定为转载时，是否继续进行群发操作。
     */
    private boolean sendIgnoreReprint = false;

    /**
     * 开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
     */
    private String clientMsgId;

    public WxMpMassTagMessage() {
        super();
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public void setSendAll(boolean sendAll) {
        if (sendAll) {
            this.tagId = null;
        }

        isSendAll = sendAll;
    }
}
