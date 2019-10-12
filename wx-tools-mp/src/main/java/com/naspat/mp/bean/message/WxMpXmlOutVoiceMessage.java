package com.naspat.mp.bean.message;

import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutVoiceMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = 7981836282368814443L;

    @XStreamAlias("Voice")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public WxMpXmlOutVoiceMessage() {
        this.msgType = WxConstant.XmlMsgType.VOICE;
    }

}
