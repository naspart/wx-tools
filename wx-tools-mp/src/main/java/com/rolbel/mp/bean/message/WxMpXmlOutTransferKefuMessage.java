package com.rolbel.mp.bean.message;

import com.rolbel.common.api.WxConstant;
import com.rolbel.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutTransferKefuMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -1409026923648216923L;

    @XStreamAlias("TransInfo")
    protected TransInfo transInfo;

    public WxMpXmlOutTransferKefuMessage() {
        this.msgType = WxConstant.KefuMsgType.TRANSFER_CUSTOMER_SERVICE;
    }

    @XStreamAlias("TransInfo")
    @Data
    public static class TransInfo implements Serializable {
        private static final long serialVersionUID = -6966766949105259525L;

        @XStreamAlias("KfAccount")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String kfAccount;
    }
}
