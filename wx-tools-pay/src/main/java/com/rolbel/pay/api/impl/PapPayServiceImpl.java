package com.rolbel.pay.api.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.rolbel.pay.api.PapPayService;
import com.rolbel.pay.api.WxPayService;
import com.rolbel.pay.bean.pappay.request.*;
import com.rolbel.pay.bean.pappay.result.*;
import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.rolbel.pay.config.WxPayConfig;
import com.rolbel.pay.exception.WxPayException;
import com.rolbel.pay.util.SignUtils;
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
        return this.getMpSignUrl(null, request);
    }

    @Override
    public String getMpSignUrl(WxPayConfig wxPayConfig, PapPayMpSignRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

        Map<String, String> sPara = Maps.transformValues(SignUtils.xmlBean2Map(request), val -> {
            try {
                assert val != null;
                return URLEncoder.encode(val, "utf-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        });

        return "https://api.mch.weixin.qq.com/papay/entrustweb?" + Joiner.on("&").withKeyValueSeparator("=").join(sPara);
    }

    @Override
    public PapPayPayAndSignResult payAndSign(PapPayPayAndSignRequest request) throws WxPayException {
        return this.payAndSign(null, request);
    }

    @Override
    public PapPayPayAndSignResult payAndSign(WxPayConfig wxPayConfig, PapPayPayAndSignRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

        String url = this.payService.getPayBaseUrl() + "/pay/contractorder";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayPayAndSignResult result = BaseWxPayResult.fromXML(responseContent, PapPayPayAndSignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPaySignNotifyResult parseSignNotifyResult(String xmlData) throws WxPayException {
        return this.parseSignNotifyResult(null, xmlData);
    }

    @Override
    public PapPaySignNotifyResult parseSignNotifyResult(WxPayConfig wxPayConfig, String xmlData) throws WxPayException {
        if (wxPayConfig == null) {
            wxPayConfig = this.payService.getConfig();
        }

        try {
            log.debug("微信免密支付签约异步通知请求参数：{}", xmlData);
            PapPaySignNotifyResult result = PapPaySignNotifyResult.fromXML(xmlData);
            log.debug("微信免密支付签约异步通知请求解析后的对象：{}", result);
            result.checkResult(this.payService, wxPayConfig.getSignType(), true);

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
        return this.queryContract(null, request);
    }

    @Override
    public PapPayContractQueryResult queryContract(WxPayConfig wxPayConfig, PapPayContractQueryRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

        String url = this.payService.getPayBaseUrl() + "/papay/querycontract";
        String responseContent = this.payService.post(url, request.toXML(), false);
        PapPayContractQueryResult result = BaseWxPayResult.fromXML(responseContent, PapPayContractQueryResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayApplyPayResult applyPay(PapPayApplyPayRequest request) throws WxPayException {
        return this.applyPay(null, request);
    }

    @Override
    public PapPayApplyPayResult applyPay(WxPayConfig wxPayConfig, PapPayApplyPayRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

        String url = this.payService.getPayBaseUrl() + "/pay/pappayapply";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayApplyPayResult result = BaseWxPayResult.fromXML(responseContent, PapPayApplyPayResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayOrderNotifyResult parseApplyPayNotifyResult(String xmlData) throws WxPayException {
        return this.parseApplyPayNotifyResult(null, xmlData);
    }

    @Override
    public PapPayOrderNotifyResult parseApplyPayNotifyResult(WxPayConfig wxPayConfig, String xmlData) throws WxPayException {
        if (wxPayConfig == null) {
            wxPayConfig = this.payService.getConfig();
        }

        try {
            log.debug("微信免密支付异步通知请求参数：{}", xmlData);
            PapPayOrderNotifyResult result = PapPayOrderNotifyResult.fromXML(xmlData);
            log.debug("微信免密支付异步通知请求解析后的对象：{}", result);

            result.checkResult(this.payService, wxPayConfig.getSignType(), true);

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
        return this.unsign(null, request);
    }

    @Override
    public PapPayUnsignResult unsign(WxPayConfig wxPayConfig, PapPayUnsignRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

        String url = this.payService.getPayBaseUrl() + "/pappay/deletecontract";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayUnsignResult result = BaseWxPayResult.fromXML(responseContent, PapPayUnsignResult.class);
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }

    @Override
    public PapPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        return this.queryOrder(null, transactionId, outTradeNo);
    }

    @Override
    public PapPayOrderQueryResult queryOrder(WxPayConfig wxPayConfig, String transactionId, String outTradeNo) throws WxPayException {
        PapPayOrderQueryRequest request = new PapPayOrderQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));

        return this.queryOrder(wxPayConfig, request);
    }

    @Override
    public PapPayOrderQueryResult queryOrder(PapPayOrderQueryRequest request) throws WxPayException {
        return this.queryOrder(null, request);
    }

    @Override
    public PapPayOrderQueryResult queryOrder(WxPayConfig wxPayConfig, PapPayOrderQueryRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

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
    public PapPayRefundQueryResult queryRefund(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxPayException {
        return this.queryRefund(null, transactionId, outTradeNo, outRefundNo, refundId);
    }

    @Override
    public PapPayRefundQueryResult queryRefund(WxPayConfig wxPayConfig, String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxPayException {
        PapPayRefundQueryRequest request = new PapPayRefundQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));
        request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
        request.setRefundId(StringUtils.trimToNull(refundId));

        return this.queryRefund(wxPayConfig, request);
    }

    @Override
    public PapPayRefundQueryResult queryRefund(PapPayRefundQueryRequest request) throws WxPayException {
        return this.queryRefund(null, request);
    }

    @Override
    public PapPayRefundQueryResult queryRefund(WxPayConfig wxPayConfig, PapPayRefundQueryRequest request) throws WxPayException {
        if (wxPayConfig == null) {
            request.checkAndSign(this.payService.getConfig());
        } else {
            request.checkAndSign(wxPayConfig);
        }

        String url = this.payService.getPayBaseUrl() + "/pay/refundquery";
        String responseContent = this.payService.post(url, request.toXML(), false);

        PapPayRefundQueryResult result = BaseWxPayResult.fromXML(responseContent, PapPayRefundQueryResult.class);
        result.composeRefundRecords();
        result.checkResult(this.payService, request.getSignType(), true);

        return result;
    }
}
