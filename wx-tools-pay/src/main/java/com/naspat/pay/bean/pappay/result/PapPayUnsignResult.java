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
public class PapPayUnsignResult extends BaseWxPayResult {
    private static final long serialVersionUID = -5463597451211561263L;

    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("contract_code")
    private String contractCode;
}
