package com.naspat.pay.api.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.naspat.pay.api.PapPayService;
import com.naspat.pay.api.WxPayService;
import com.naspat.pay.bean.pappay.request.*;
import com.naspat.pay.bean.pappay.result.*;
import com.naspat.pay.bean.result.BaseWxPayResult;
import com.naspat.pay.exception.WxPayException;
import com.naspat.pay.util.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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
        request.checkAndSign(this.payService.getWxPayConfig());

        Map<String, String> sPara = Maps.transformValues(SignUtils.xmlBean2Map(request), val -> {
            try {
                assert val != null;
                return URLEncoder.encode(val, "utf-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        });
        // 注意微信免密支付签约url中不能有sign_type参数
        sPara = Maps.filterKeys(sPara, key -> key != null && !key.equals("sign_type"));

        return "https://api.mch.weixin.qq.com/papay/entrustweb?" + Joiner.on("&").withKeyValueSeparator("=").join(sPara);
    }

    @Override
    public String getAppSignUrl(PapPayAppSignRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/papay/preentrustweb";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PayPayAppSignResult result = BaseWxPayResult.fromXML(responseContent, PayPayAppSignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result.getPreEntrustwebId();
    }

    @Override
    public PapPayPayAndSignResult payAndSign(PapPayPayAndSignRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/contractorder";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayPayAndSignResult result = BaseWxPayResult.fromXML(responseContent, PapPayPayAndSignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPaySignNotifyResult parseSignNotifyResult(String xmlData) throws WxPayException {
        try {
            log.debug("微信免密支付签约异步通知请求参数：{}", xmlData);
            PapPaySignNotifyResult result = PapPaySignNotifyResult.fromXML(xmlData);
            log.debug("微信免密支付签约异步通知请求解析后的对象：{}", result);
            result.checkResult(this.payService, this.payService.getWxPayConfig().getSignType(), true);

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
    public PapPayContractQueryResult queryContract(PapPayContractQueryRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/papay/querycontract";
        String responseContent = this.payService.post(url, request.toXML(), false);
        PapPayContractQueryResult result = BaseWxPayResult.fromXML(responseContent, PapPayContractQueryResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayApplyPayResult applyPay(PapPayApplyPayRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/pappayapply";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayApplyPayResult result = BaseWxPayResult.fromXML(responseContent, PapPayApplyPayResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayOrderNotifyResult parseApplyPayNotifyResult(String xmlData) throws WxPayException {
        try {
            log.debug("微信免密支付异步通知请求参数：{}", xmlData);
            PapPayOrderNotifyResult result = PapPayOrderNotifyResult.fromXML(xmlData);
            log.debug("微信免密支付异步通知请求解析后的对象：{}", result);

            result.checkResult(this.payService, this.payService.getWxPayConfig().getSignType(), true);

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
    public PapPayUnsignResult unsign(PapPayUnsignRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/pappay/deletecontract";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayUnsignResult result = BaseWxPayResult.fromXML(responseContent, PapPayUnsignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        PapPayOrderQueryRequest request = new PapPayOrderQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));

        return this.queryOrder(request);
    }

    @Override
    public PapPayOrderQueryResult queryOrder(PapPayOrderQueryRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/paporderquery";
        String responseContent = this.payService.post(url, request.toXML(), false);
        if (StringUtils.isBlank(responseContent)) {
            throw new WxPayException("无响应结果");
        }

        PapPayOrderQueryResult result = BaseWxPayResult.fromXML(responseContent, PapPayOrderQueryResult.class);
        result.composeCoupons();
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayRefundResult refund(PapPayRefundRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/secapi/pay/refund";
        String responseContent = this.payService.post(url, request.toXML(), true);
        PapPayRefundResult result = BaseWxPayResult.fromXML(responseContent, PapPayRefundResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayRefundQueryResult queryRefund(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxPayException {
        PapPayRefundQueryRequest request = new PapPayRefundQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));
        request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
        request.setRefundId(StringUtils.trimToNull(refundId));

        return this.queryRefund(request);
    }

    @Override
    public PapPayRefundQueryResult queryRefund(PapPayRefundQueryRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getWxPayConfig());

        String url = this.payService.getPayBaseUrl() + "/pay/refundquery";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayRefundQueryResult result = BaseWxPayResult.fromXML(responseContent, PapPayRefundQueryResult.class);
        result.composeRefundRecords();
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }
}
