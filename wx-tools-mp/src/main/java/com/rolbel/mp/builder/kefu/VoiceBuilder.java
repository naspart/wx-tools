package com.rolbel.mp.builder.kefu;


import com.rolbel.common.api.WxConstant;
import com.rolbel.mp.bean.kefu.WxMpKefuMessage;

/**
 * 语音消息builder
 * <pre>
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.VOICE().mediaId(...).toUser(...).build();
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
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
