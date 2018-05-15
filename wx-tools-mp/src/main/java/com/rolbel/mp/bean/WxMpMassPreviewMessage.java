package com.rolbel.mp.bean;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author miller
 */
@Data
public class WxMpMassPreviewMessage implements Serializable {
    private static final long serialVersionUID = 9095211638358424020L;

    private String toWxUserName;
    private String toWxUserOpenid;
    /**
     * <pre>
     * 消息类型
     * 请使用
     * {@link com.rolbel.common.api.WxConstant.MassMsgType#IMAGE}
     * {@link com.rolbel.common.api.WxConstant.MassMsgType#MPNEWS}
     * {@link com.rolbel.common.api.WxConstant.MassMsgType#TEXT}
     * {@link com.rolbel.common.api.WxConstant.MassMsgType#MPVIDEO}
     * {@link com.rolbel.common.api.WxConstant.MassMsgType#VOICE}
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
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
