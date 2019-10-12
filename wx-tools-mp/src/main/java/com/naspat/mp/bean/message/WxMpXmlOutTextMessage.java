package com.naspat.mp.bean.message;

import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XStreamAlias("xml")
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutTextMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -6354764352878371915L;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    public WxMpXmlOutTextMessage() {
        this.msgType = WxConstant.XmlMsgType.TEXT;
    }
}
