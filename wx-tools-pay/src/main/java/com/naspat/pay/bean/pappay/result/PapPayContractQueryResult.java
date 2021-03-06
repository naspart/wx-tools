package com.naspat.pay.bean.pappay.result;

import com.naspat.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayContractQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 2663492862228974666L;

    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("request_serial")
    private String requestSerial;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("contract_display_account")
    private String contractDisplayAccount;

    @XStreamAlias("contract_state")
    private Integer contractState;

    @XStreamAlias("contract_signed_time")
    private String contractSignedTime;

    @XStreamAlias("contract_expired_time")
    private String contractExpiredTime;

    @XStreamAlias("contract_terminated_time")
    private String contractTerminatedTime;

    @XStreamAlias("contract_termination_mode")
    private Integer contractTerminationMode;

    @XStreamAlias("contract_termination_remark")
    private String contractTerminationRemark;

    @XStreamAlias("openid")
    private String openId;
}
