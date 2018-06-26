package com.rolbel.pay.bean.result;

import com.rolbel.common.util.ToStringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class WxPayBillResult implements Serializable {
    private static final long serialVersionUID = -8743383996132887472L;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    /**
     * 对账返回对象
     */
    private List<WxPayBillBaseResult> wxPayBillBaseResultLst;
    /**
     * 总交易单数
     */
    private String totalRecord;
    /**
     * 总交易额
     */
    private String totalFee;
    /**
     * 总退款金额
     */
    private String totalRefundFee;
    /**
     * 总代金券或立减优惠退款金额
     */
    private String totalCouponFee;
    /**
     * 手续费总金额
     */
    private String totalPoundageFee;

}
