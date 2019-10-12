package com.naspat.pay.bean.pappay.result;

import com.naspat.common.util.xml.XStreamInitializer;
import com.naspat.pay.bean.result.BaseWxPayResult;
import com.naspat.pay.converter.PapPayOrderNotifyResultConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayOrderNotifyResult extends BaseWxPayResult {
    private static final long serialVersionUID = -6336827289132459892L;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    @XStreamAlias("bank_type")
    private String bankType;

    @XStreamAlias("total_fee")
    private Integer totalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("cash_fee")
    private String cashFee;

    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    @XStreamAlias("trade_state")
    private String tradeState;

    @XStreamAlias("coupon_fee")
    private Integer coupon_fee;

    @XStreamAlias("coupon_count")
    private Integer couponCount;

    private List<PapPayOrderNotifyCoupon> couponList;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("time_end")
    private String timeEnd;

    @XStreamAlias("contract_id")
    private String contractId;

    public static PapPayOrderNotifyResult fromXML(String xmlString) {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(PapPayOrderNotifyResult.class);
        xstream.allowTypes(new Class[]{PapPayOrderNotifyResult.class});
        xstream.registerConverter(new PapPayOrderNotifyResultConverter(xstream.getMapper(), xstream.getReflectionProvider()));

        PapPayOrderNotifyResult result = (PapPayOrderNotifyResult) xstream.fromXML(xmlString);
        result.setXmlString(xmlString);

        return result;
    }
}
