package com.naspat.pay.bean.request;

import com.naspat.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 撤销订单请求类
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderReverseRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -6700174089038302462L;

    /**
     * <pre>
     * 微信订单号
     * transaction_id
     * String(28)
     * 1217752501201400000000000000
     * 微信的订单号，优先使用
     * </pre>
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * <pre>
     * 商户订单号
     * out_trade_no
     * String(32)
     * 1217752501201400000000000000
     * 商户系统内部的订单号
     * transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
     * </pre>
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @Override
    protected void checkConstraints() throws WxPayException {
        if (StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)) {
            throw new WxPayException("transaction_id 和 out_trade_no不能同时为空！");
        }
    }

}
