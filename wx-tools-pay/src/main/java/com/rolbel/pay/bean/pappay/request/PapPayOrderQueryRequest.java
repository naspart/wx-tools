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
public class PapPayOrderQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 3021044842879976119L;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @Override
    protected void checkConstraints() throws WxPayException {

    }
}