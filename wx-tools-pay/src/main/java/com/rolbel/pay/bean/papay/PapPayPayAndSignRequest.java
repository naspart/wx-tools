package com.rolbel.pay.bean.papay;

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
public class PapPayPayAndSignRequest extends BaseWxPayRequest {
    @XStreamAlias("contract_mchid")
    private String contractMchId;

    @XStreamAlias("contract_appid")
    private String contractAppId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("body")
    private String body;

    @XStreamAlias("detail")
    private String detail;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("notify_url")
    private String notifyUrl;

    @XStreamAlias("total_fee")
    private Integer totalFee;

    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    @XStreamAlias("time_start")
    private String timeStart;

    @XStreamAlias("time_expire")
    private String timeExpire;

    @XStreamAlias("goods_tag")
    private String goodsTag;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("product_id")
    private String productId;

    @XStreamAlias("limit_pay")
    private String limitPay;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("plan_id")
    private String planId;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("request_serial")
    private Long requestSerial;

    @XStreamAlias("contract_display_account")
    private String contractDisplayAccount;

    @XStreamAlias("contract_notify_url")
    private String contractNotifyUrl;

    @Override
    protected void checkConstraints() throws WxPayException {

    }
}
