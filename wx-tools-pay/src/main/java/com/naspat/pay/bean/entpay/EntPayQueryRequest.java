package com.naspat.pay.bean.entpay;

import com.naspat.common.annotation.Required;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.pay.bean.request.BaseWxPayRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

/**
 * <pre>
 * 企业付款请求对象.
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class EntPayQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 2914184199717361165L;

    /**
     * <pre>
     * 字段名：商户订单号.
     * 变量名：partner_trade_no
     * 是否必填：是
     * 示例值：10000098201411111234567890
     * 类型：String
     * 描述商户订单号
     * </pre>
     */
    @Required
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"sign_type"};
    }

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }
}
