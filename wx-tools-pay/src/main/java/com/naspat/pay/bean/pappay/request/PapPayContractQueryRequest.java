package com.naspat.pay.bean.pappay.request;

import com.naspat.common.annotation.Required;
import com.naspat.pay.bean.request.BaseWxPayRequest;
import com.naspat.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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

    @Required
    @XStreamAlias("version")
    private String version;

    @Override
    protected boolean ignoreNonceStr() {
        return true;
    }

    @Override
    protected void checkConstraints() throws WxPayException {
        if (StringUtils.isNoneBlank(contractId, planId, contractCode)) {
            throw new WxPayException("contract_id 和 contract_code,plan_id 不能全部同时提供，必须选择一种查询方式");
        }

        if (StringUtils.isAllBlank(contractId, planId, contractCode)) {
            throw new WxPayException("contract_id 和 contract_code,plan_id 不能同时为空，必须选择一种查询方式");
        }

        if (StringUtils.isBlank(contractId) && StringUtils.isAnyBlank(planId, contractCode)) {
            throw new WxPayException("使用模板id查询时，contract_code 和 plan_id 不能为空");
        }

        if (StringUtils.isNotBlank(contractId) && (StringUtils.isNotBlank(planId) || StringUtils.isNotBlank(contractCode))) {
            throw new WxPayException("使用协议id查询时，contract_code 和 plan_id 不需要提供");
        }
    }
}
