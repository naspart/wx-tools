package com.rolbel.pay.bean.notify;

import com.rolbel.common.util.json.WxGsonBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付异步通知代金券详细
 */
@Data
@NoArgsConstructor
public class WxPayOrderNotifyCoupon implements Serializable {
    private static final long serialVersionUID = -5779557703231905901L;

    private String couponId;
    private String couponType;
    private Integer couponFee;

    public Map<String, String> toMap(int index) {
        Map<String, String> map = new HashMap<>();
        map.put("coupon_id_" + index, this.getCouponId());
        map.put("coupon_type_" + index, this.getCouponType());
        map.put("coupon_fee_" + index, this.getCouponFee() + "");
        return map;
    }

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }
}
