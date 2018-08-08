package com.rolbel.pay.api;

import com.rolbel.pay.bean.papay.*;
import com.rolbel.pay.exception.WxPayException;

public interface PapPayService {
    String getMpSignUrl(PapPayMpSignRequest request) throws WxPayException;

    PapPayPayAndSignResult payAndSign(PapPayPayAndSignRequest request) throws WxPayException;

    PapPaySignNotifyResult parseSignNotifyResult(String xmlData) throws WxPayException;

    PapPayQueryContractResult queryContract(PapPayQueryContractRequest request) throws WxPayException;

    PapPayApplyPayResult applyPay(PapPayApplyPayRequest request) throws WxPayException;

    PapPayUnsignResult unsign(PapPayUnsignRequest request) throws WxPayException;

    PapPayQueryOrderResult queryOrder(String transactionId, String outTradeNo) throws WxPayException;

    PapPayQueryOrderResult queryOrder(PapPayQueryOrderRequest request) throws WxPayException;

    PapPayQueryRefundResult queryRefund(String transactionId, String outTradeNo, String outRefundNo, String refundId)
            throws WxPayException;

    PapPayQueryRefundResult queryRefund(PapPayQueryRefundRequest request) throws WxPayException;
}
