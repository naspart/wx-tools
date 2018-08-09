package com.rolbel.pay.bean.pappay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PapPayRefundCouponInfo implements Serializable {
    private static final long serialVersionUID = 987927726969137784L;

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