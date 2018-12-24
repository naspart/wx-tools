package com.rolbel.mp.bean.message;

import com.rolbel.common.api.WxConstant;
import com.rolbel.common.util.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@XStreamAlias("xml")
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutImageMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -658583560425544792L;

    @XStreamAlias("Image")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public WxMpXmlOutImageMessage() {
        this.msgType = WxConstant.XmlMsgType.IMAGE;
    }

}
