package com.rolbel.pay.bean.pappay;

import com.google.common.collect.Lists;
import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayApplyPayNotifyResult extends BaseWxPayResult {
    private static final long serialVersionUID = -4120696098687726330L;

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

    private List<Coupon> coupons;

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

    /**
     * 通过xml组装coupons属性内容
     */
    public void composeCoupons() {
        if (this.couponCount != null && this.couponCount > 0) {
            this.coupons = Lists.newArrayList();
            for (int i = 0; i < this.couponCount; i++) {
                this.coupons.add(new PapPayApplyPayNotifyResult.Coupon(this.getXmlValue("xml/coupon_type_" + i),
                        this.getXmlValue("xml/coupon_id_" + i),
                        this.getXmlValueAsInt("xml/coupon_fee_" + i)));
            }
        }
    }

    @Data
    @Builder(builderMethodName = "newBuilder")
    @AllArgsConstructor
    public static class Coupon {
        /**
         * <pre>代金券类型
         * coupon_type_$n
         * 否
         * String
         * CASH
         * <li>CASH--充值代金券
         * <li>NO_CASH---非充值代金券
         * 	订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
         * </pre>
         */
        private String couponType;

        /**
         * <pre>代金券ID
         * coupon_id_$n
         * 否
         * String(20)
         * 10000
         * 代金券ID, $n为下标，从0开始编号
         * </pre>
         */
        private String couponId;

        /**
         * <pre>单个代金券支付金额
         * coupon_fee_$n
         * 否
         * Int
         * 100
         * 单个代金券支付金额, $n为下标，从0开始编号
         * </pre>
         */
        private Integer couponFee;
    }
}
