package com.rolbel.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.rolbel.common.api.WxConstant;
import com.rolbel.common.util.xml.XStreamMediaIdConverter;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutVoiceMessage extends WxCpXmlOutMessage {
    private static final long serialVersionUID = -2407927683903221420L;

    @XStreamAlias("Voice")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public WxCpXmlOutVoiceMessage() {
        this.msgType = WxConstant.XmlMsgType.VOICE;
    }
}
