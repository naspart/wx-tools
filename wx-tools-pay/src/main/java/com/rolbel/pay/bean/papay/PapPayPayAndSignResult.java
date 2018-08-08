package com.rolbel.pay.bean.papay;

import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayPayAndSignResult extends BaseWxPayResult {
    @XStreamAlias("contract_result_code")
    private String contractResultCode;

    @XStreamAlias("contract_err_code")
    private String contractErrCode;

    @XStreamAlias("contract_err_code_des")
    private String contractErrCodeDes;

    @XStreamAlias("prepay_id")
    private String prepayId;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("code_url")
    private String codeUrl;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("request_serial")
    private String requestSerial;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("contract_display_account")
    private String contractDisplayAccount;

    @XStreamAlias("mweb_url")
    private String mwebUrl;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;
}
