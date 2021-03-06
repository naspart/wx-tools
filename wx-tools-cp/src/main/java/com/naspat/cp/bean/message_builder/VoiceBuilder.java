package com.naspat.cp.bean.message_builder;

import com.naspat.common.api.WxConstant;
import com.naspat.cp.bean.WxCpMessage;

/**
 * 语音消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE().mediaId(...).toUser(...).build();
 * </pre>
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
    private String mediaId;

    public VoiceBuilder() {
        this.msgType = WxConstant.KefuMsgType.VOICE;
    }

    public VoiceBuilder mediaId(String media_id) {
        this.mediaId = media_id;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
