package com.naspat.pay.bean.notify;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.common.util.xml.XStreamInitializer;

import java.io.Serializable;

/**
 * 微信支付订单和退款的异步通知共用的响应类
 */
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayNotifyResponse implements Serializable {
    private static final long serialVersionUID = 4890976552441454029L;

    @XStreamOmitField
    private transient static final String FAIL = "FAIL";

    @XStreamOmitField
    private transient static final String SUCCESS = "SUCCESS";

    @XStreamAlias("return_code")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String returnCode;

    @XStreamConverter(value = XStreamCDataConverter.class)
    @XStreamAlias("return_msg")
    private String returnMsg;

    public static String fail(String msg) {
        WxPayNotifyResponse response = new WxPayNotifyResponse(FAIL, msg);
        XStream xstream = XStreamInitializer.getInstance();
        xstream.autodetectAnnotations(true);

        return xstream.toXML(response);
    }

    public static String success(String msg) {
        WxPayNotifyResponse response = new WxPayNotifyResponse(SUCCESS, msg);
        XStream xstream = XStreamInitializer.getInstance();
        xstream.autodetectAnnotations(true);

        return xstream.toXML(response);
    }
}
