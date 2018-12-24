package com.rolbel.pay.bean.pappay.result;

import com.rolbel.common.util.json.WxGsonBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PapPayOrderNotifyCoupon implements Serializable {
    private static final long serialVersionUID = 3224969075950281064L;

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

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }
}
