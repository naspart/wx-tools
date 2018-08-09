package com.rolbel.pay.bean.pappay;

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
public class PapPayUnsignRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 3974080335768076080L;

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
    protected void checkConstraints() throws WxPayException {

    }
}
