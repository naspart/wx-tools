package com.rolbel.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayRedpackQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 1475906956207862717L;

    /**
     * 商户订单号
     * mch_billno
     * 是
     * 10000098201411111234567890
     * String(28)
     * 商户发放红包的商户订单号
     */
    @XStreamAlias("mch_billno")
    private String mchBillNo;

    /**
     * 订单类型
     * bill_type
     * 是
     * MCHT
     * String(32)
     * MCHT:通过商户订单号获取红包信息。
     */
    @XStreamAlias("bill_type")
    private String billType;

    @Override
    protected void checkConstraints() {

    }
}
