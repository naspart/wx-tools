package com.rolbel.pay.api.impl;

import com.rolbel.pay.api.PapPayService;
import com.rolbel.pay.api.WxPayService;
import com.rolbel.pay.bean.pappay.*;
import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.rolbel.pay.exception.WxPayException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *  免密支付服务
 * </pre>
 */
public class PapPayServiceImpl implements PapPayService {
    private static final Logger log = LoggerFactory.getLogger(PapPayServiceImpl.class);
    private WxPayService payService;

    PapPayServiceImpl(WxPayService payService) {
        this.payService = payService;
    }

    @Override
    public String getMpSignUrl(PapPayMpSignRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());
        return null;
    }

    @Override
    public PapPayPayAndSignResult payAndSign(PapPayPayAndSignRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/contractorder";
        String responseContent = this.payService.post(url, request.toXML(), true);

        PapPayPayAndSignResult result = BaseWxPayResult.fromXML(responseContent, PapPayPayAndSignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPaySignNotifyResult parseSignNotifyResult(String xmlData) throws WxPayException {
        try {
            log.debug("微信支付异步通知请求参数：{}", xmlData);
            PapPaySignNotifyResult result = PapPaySignNotifyResult.fromXML(xmlData);
            log.debug("微信支付异步通知请求解析后的对象：{}", result);
            result.checkResult(this.payService, this.payService.getConfig().getSignType(), true);

            return result;
        } catch (WxPayException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WxPayException("发生异常，" + e.getMessage(), e);
        }
    }

    @Override
    public PapPayQueryContractResult queryContract(PapPayQueryContractRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/pappay/querycontract";
        String responseContent = this.payService.post(url, request.toXML(), true);
        PapPayQueryContractResult result = BaseWxPayResult.fromXML(responseContent, PapPayQueryContractResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayApplyPayResult applyPay(PapPayApplyPayRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/pappayapply";
        String responseContent = this.payService.post(url, request.toXML(), true);

        PapPayApplyPayResult result = BaseWxPayResult.fromXML(responseContent, PapPayApplyPayResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayUnsignResult unsign(PapPayUnsignRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/pappay/deletecontract";
        String responseContent = this.payService.post(url, request.toXML(), true);

        PapPayUnsignResult result = BaseWxPayResult.fromXML(responseContent, PapPayUnsignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }


    @Override
    public PapPayQueryOrderResult queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        PapPayQueryOrderRequest request = new PapPayQueryOrderRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));

        return this.queryOrder(request);
    }

    @Override
    public PapPayQueryOrderResult queryOrder(PapPayQueryOrderRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/orderquery";
        String responseContent = this.payService.post(url, request.toXML(), false);
        if (StringUtils.isBlank(responseContent)) {
            throw new WxPayException("无响应结果");
        }

        PapPayQueryOrderResult result = BaseWxPayResult.fromXML(responseContent, PapPayQueryOrderResult.class);
        result.composeCoupons();
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayQueryRefundResult queryRefund(String transactionId, String outTradeNo, String outRefundNo, String refundId)
            throws WxPayException {
        PapPayQueryRefundRequest request = new PapPayQueryRefundRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));
        request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
        request.setRefundId(StringUtils.trimToNull(refundId));

        return this.queryRefund(request);
    }

    @Override
    public PapPayQueryRefundResult queryRefund(PapPayQueryRefundRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/refundquery";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayQueryRefundResult result = BaseWxPayResult.fromXML(responseContent, PapPayQueryRefundResult.class);
        result.composeRefundRecords();
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }
}
