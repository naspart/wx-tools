package com.rolbel.pay.bean.pappay.request;

import com.rolbel.pay.bean.request.BaseWxPayRequest;
import com.rolbel.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayRefundQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -7954009499727423597L;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("out_refund_no")
    private String outRefundNo;

    @XStreamAlias("refund_id")
    private String refundId;

    @Override
    protected boolean ignoreNonceStr() {
        return true;
    }

    @Override
    protected void checkConstraints() throws WxPayException {

    }
}
