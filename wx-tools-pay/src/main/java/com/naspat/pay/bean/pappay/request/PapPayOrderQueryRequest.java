package com.naspat.pay.bean.pappay.request;

import com.naspat.pay.bean.request.BaseWxPayRequest;
import com.naspat.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayOrderQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 3021044842879976119L;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @Override
    protected boolean ignoreNonceStr() {
        return false;
    }

    @Override
    protected void checkConstraints() throws WxPayException {
        if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)) ||
                (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo))) {
            throw new WxPayException("transaction_id 和 out_trade_no 不能同时存在或同时为空，必须二选一");
        }
    }
}
