package com.naspat.pay.bean.pappay.result;

import com.naspat.common.util.xml.XStreamInitializer;
import com.naspat.pay.bean.result.BaseWxPayResult;
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
    private static final long serialVersionUID = 761824556680279050L;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("openid")
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
        xstream.allowTypes(new Class[]{PapPaySignNotifyResult.class});

        PapPaySignNotifyResult result = (PapPaySignNotifyResult) xstream.fromXML(xmlString);
        result.setXmlString(xmlString);

        return result;
    }
}
