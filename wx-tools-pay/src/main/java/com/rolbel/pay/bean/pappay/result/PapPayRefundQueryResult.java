package com.rolbel.pay.bean.pappay.result;

import com.google.common.collect.Lists;
import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayRefundQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 8958428286813706935L;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("total_fee")
    private Integer totalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("cash_fee")
    private String cashFee;

    @XStreamAlias("refund_count")
    private Integer refundCount;

    private List<RefundRecord> refundRecords;

    /**
     * 组装生成退款记录属性的内容.
     */
    public void composeRefundRecords() {
        if (this.refundCount != null && this.refundCount > 0) {
            this.refundRecords = Lists.newArrayList();

            for (int i = 0; i < this.refundCount; i++) {
                PapPayRefundQueryResult.RefundRecord refundRecord = new PapPayRefundQueryResult.RefundRecord();
                this.refundRecords.add(refundRecord);

                refundRecord.setOutRefundNo(this.getXmlValue("xml/out_refund_no_" + i));
                refundRecord.setRefundId(this.getXmlValue("xml/refund_id_" + i));
                refundRecord.setRefundChannel(this.getXmlValue("xml/refund_channel_" + i));
                refundRecord.setRefundFee(this.getXmlValueAsInt("xml/refund_fee_" + i));
                refundRecord.setCouponRefundFee(this.getXmlValueAsInt("xml/coupon_refund_fee_" + i));
                refundRecord.setCouponRefundCount(this.getXmlValueAsInt("xml/coupon_refund_count_" + i));
                refundRecord.setRefundStatus(this.getXmlValue("xml/refund_status_" + i));
                refundRecord.setRefundRecvAccount(this.getXmlValue("xml/refund_recv_accout_" + i));

                if (refundRecord.getCouponRefundCount() == null || refundRecord.getCouponRefundCount() == 0) {
                    continue;
                }

                List<RefundRecord.PapPayRefundCouponInfo> coupons = Lists.newArrayList();
                for (int j = 0; j < refundRecord.getCouponRefundCount(); j++) {
                    coupons.add(
                            new RefundRecord.PapPayRefundCouponInfo(
                                    this.getXmlValue("xml/coupon_refund_batch_id_" + i + "_" + j),
                                    this.getXmlValue("xml/coupon_refund_id_" + i + "_" + j),
                                    this.getXmlValueAsInt("xml/coupon_refund_fee_" + i + "_" + j)
                            )
                    );
                }

                refundRecord.setRefundCoupons(coupons);
            }

        }
    }

    @Data
    @Builder(builderMethodName = "newBuilder")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundRecord {
        /**
         * <pre>
         * 字段名：商户退款单号.
         * 变量名：out_refund_no_$n
         * 是否必填：是
         * 类型：String(32)
         * 示例值：1217752501201407033233368018
         * 描述：商户退款单号
         * </pre>
         */
        @XStreamAlias("out_refund_no")
        private String outRefundNo;

        /**
         * <pre>
         * 字段名：微信退款单号.
         * 变量名：refund_id_$n
         * 是否必填：是
         * 类型：String(28)
         * 示例值：1217752501201407033233368018
         * 描述：微信退款单号
         * </pre>
         */
        @XStreamAlias("refund_id")
        private String refundId;

        /**
         * <pre>
         * 字段名：退款渠道.
         * 变量名：refund_channel_$n
         * 是否必填：否
         * 类型：String(16)
         * 示例值：ORIGINAL
         * 描述：ORIGINAL—原路退款 BALANCE—退回到余额
         * </pre>
         */
        @XStreamAlias("refund_channel")
        private String refundChannel;

        /**
         * <pre>
         * 字段名：申请退款金额.
         * 变量名：refund_fee_$n
         * 是否必填：是
         * 类型：Int
         * 示例值：100
         * 描述：退款总金额,单位为分,可以做部分退款
         * </pre>
         */
        @XStreamAlias("refund_fee")
        private Integer refundFee;

        /**
         * <pre>
         * 字段名：代金券或立减优惠退款金额.
         * 变量名：coupon_refund_fee_$n
         * 是否必填：否
         * 类型：Int
         * 示例值：100
         * 描述：代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
         * </pre>
         */
        @XStreamAlias("coupon_refund_fee")
        private Integer couponRefundFee;

        /**
         * <pre>
         * 字段名：退款代金券使用数量.
         * 变量名：coupon_refund_count_$n
         * 是否必填：否
         * 类型：Int
         * 示例值：1
         * 描述：退款代金券使用数量 ,$n为下标,从0开始编号
         * </pre>
         */
        @XStreamAlias("coupon_refund_count")
        private Integer couponRefundCount;

        private List<PapPayRefundCouponInfo> refundCoupons;

        /**
         * <pre>
         * 字段名：退款状态.
         * 变量名：refund_status_$n
         * 是否必填：是
         * 类型：String(16)
         * 示例值：SUCCESS
         * 描述：退款状态：
         *  SUCCESS—退款成功，
         *  FAIL—退款失败，
         *  PROCESSING—退款处理中，
         *  CHANGE—转入代发，
         * 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
         * </pre>
         */
        @XStreamAlias("refund_status")
        private String refundStatus;

        /**
         * <pre>
         * 字段名：退款入账账户.
         * 变量名：refund_recv_accout_$n
         * 是否必填：是
         * 类型：String(64)
         * 示例值：招商银行信用卡0403
         * 描述：取当前退款单的退款入账方，1）退回银行卡：{银行名称}{卡类型}{卡尾号}，2）退回支付用户零钱:支付用户零钱
         * </pre>
         */
        @XStreamAlias("refund_recv_accout")
        private String refundRecvAccount;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PapPayRefundCouponInfo {
            /**
             * <pre>
             * 字段名：代金券或立减优惠批次ID.
             * 变量名：coupon_refund_batch_id_$n_$m
             * 是否必填：否
             * 类型：String(20)
             * 示例值：100
             * 描述：批次ID, $n为下标，$m为下标，从0开始编号
             * </pre>
             */
            @XStreamAlias("coupon_refund_batch_id")
            private String couponRefundBatchId;

            /**
             * <pre>
             * 字段名：代金券或立减优惠ID.
             * 变量名：coupon_refund_id_$n_$m
             * 是否必填：否
             * 类型：String(20)
             * 示例值：10000
             * 描述：代金券或立减优惠ID, $n为下标，$m为下标，从0开始编号
             * </pre>
             */
            @XStreamAlias("coupon_refund_id")
            private String couponRefundId;

            /**
             * <pre>
             * 字段名：单个代金券或立减优惠支付金额.
             * 变量名：coupon_refund_fee_$n_$m
             * 是否必填：否
             * 类型：int
             * 示例值：100
             * 描述：单个代金券或立减优惠支付金额, $n为下标，$m为下标，从0开始编号
             * </pre>
             */
            @XStreamAlias("couponRefund_fee")
            private Integer couponRefundFee;
        }
    }
}
