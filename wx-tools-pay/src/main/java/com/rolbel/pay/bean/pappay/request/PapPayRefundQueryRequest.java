package com.rolbel.pay.bean.pappay.request;

import com.rolbel.pay.bean.request.BaseWxPayRequest;
import com.rolbel.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
        return false;
    }

    @Override
    protected void checkConstraints() throws WxPayException {
        if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)
                && StringUtils.isBlank(outRefundNo) && StringUtils.isBlank(refundId)) ||
                (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo)
                        && StringUtils.isNotBlank(outRefundNo) && StringUtils.isNotBlank(refundId))) {
            throw new WxPayException("transaction_id，out_trade_no，out_refund_no，refund_id 必须四选一");
        }
    }
}
