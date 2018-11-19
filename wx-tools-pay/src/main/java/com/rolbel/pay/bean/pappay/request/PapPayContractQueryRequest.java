package com.rolbel.pay.bean.pappay.request;

import com.rolbel.pay.bean.request.BaseWxPayRequest;
import com.rolbel.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayContractQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -8606801566913857719L;

    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("contract_code")
    private String contractCode;

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
