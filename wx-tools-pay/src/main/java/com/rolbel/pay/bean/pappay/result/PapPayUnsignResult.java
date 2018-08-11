package com.rolbel.pay.bean.pappay.result;

import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayUnsignResult extends BaseWxPayResult {
    private static final long serialVersionUID = -1097442247005407442L;

    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("contract_code")
    private String contractCode;
}
