package com.naspat.pay.bean.pappay.request;

import com.naspat.pay.bean.request.BaseWxPayRequest;
import com.naspat.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayUnsignRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -258812134947071465L;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("contract_termination_remark")
    private String contractTerminationRemark;

    @XStreamAlias("version")
    private String version;

    @Override
    protected boolean ignoreNonceStr() {
        return true;
    }

    @Override
    protected void checkConstraints() throws WxPayException {

    }
}
