package com.naspat.cp.bean.outxml_builder;

import com.naspat.cp.bean.WxCpXmlOutVoiceMessage;

/**
 * 语音消息builder
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxCpXmlOutVoiceMessage> {

    private String mediaId;

    public VoiceBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxCpXmlOutVoiceMessage build() {
        WxCpXmlOutVoiceMessage m = new WxCpXmlOutVoiceMessage();
        setCommon(m);
        m.setMediaId(this.mediaId);
        return m;
    }

}
