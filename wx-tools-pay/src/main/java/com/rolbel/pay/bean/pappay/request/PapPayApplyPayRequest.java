package com.rolbel.pay.bean.pappay.request;

import com.rolbel.common.annotation.Required;
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
public class PapPayApplyPayRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -623395514483325027L;

    @Required
    @XStreamAlias("body")
    private String body;

    @XStreamAlias("detail")
    private String detail;

    @XStreamAlias("attach")
    private String attach;

    @Required
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @Required
    @XStreamAlias("total_fee")
    private Integer totalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @Required
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    @XStreamAlias("goods_tag")
    private String goodsTag;

    @Required
    @XStreamAlias("notify_url")
    private String notifyUrl;

    @Required
    @XStreamAlias("trade_type")
    private String tradeType;

    @Required
    @XStreamAlias("contract_id")
    private String contractId;

    @XStreamAlias("clientip")
    private String clientIp;

    @XStreamAlias("deviceid")
    private String deviceId;

    @XStreamAlias("mobile")
    private String mobile;

    @XStreamAlias("email")
    private String email;

    @XStreamAlias("qq")
    private String qq;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("creid")
    private String creId;

    @XStreamAlias("outerid")
    private String outerId;

    @XStreamAlias("timestamp")
    private String timestamp;

    @Override
    protected boolean ignoreNonceStr() {
        return false;
    }

    @Override
    protected void checkConstraints() throws WxPayException {

    }
}
