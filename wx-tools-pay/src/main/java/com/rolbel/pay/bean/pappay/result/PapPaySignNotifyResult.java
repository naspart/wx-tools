package com.rolbel.pay.bean.pappay.result;

import com.rolbel.common.util.xml.XStreamInitializer;
import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.rolbel.pay.converter.WxPayOrderNotifyResultConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPaySignNotifyResult extends BaseWxPayResult {
    private static final long serialVersionUID = -7434940415600814962L;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("plan_id")
    private String openId;

    @XStreamAlias("change_type")
    private String changeType;

    @XStreamAlias("operate_time")
    private String operateTime;

    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("contract_expired_time")
    private String contractExpiredTime;

    @XStreamAlias("contract_termination_mode")
    private Integer contractTerminationMode;

    @XStreamAlias("request_serial")
    private String requestSerial;

    public static PapPaySignNotifyResult fromXML(String xmlString) {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(PapPaySignNotifyResult.class);
        xstream.registerConverter(new WxPayOrderNotifyResultConverter(xstream.getMapper(), xstream.getReflectionProvider()));
        PapPaySignNotifyResult result = (PapPaySignNotifyResult) xstream.fromXML(xmlString);
        result.setXmlString(xmlString);

        return result;
    }
}
