package com.naspat.pay.bean.entpay;

import com.naspat.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 企业付款到银行卡的响应结果.
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class EntPayBankResult extends BaseWxPayResult {
    private static final long serialVersionUID = 955976258318456771L;

    /**
     * 代付金额.
     */
    @XStreamAlias("amount")
    private Integer amount;

    /**
     * 商户企业付款单号.
     */
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    //############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
    /**
     * 微信企业付款单号.
     * 代付成功后，返回的内部业务单号
     */
    @XStreamAlias("payment_no")
    private String paymentNo;

    /**
     * 手续费金额.
     * RMB：分
     */
    @XStreamAlias("cmms_amt")
    private Integer cmmsAmount;
}
