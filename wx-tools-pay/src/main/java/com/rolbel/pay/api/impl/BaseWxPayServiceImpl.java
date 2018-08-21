package com.rolbel.pay.api.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.rolbel.pay.api.EntPayService;
import com.rolbel.pay.api.PapPayService;
import com.rolbel.pay.api.WxPayService;
import com.rolbel.pay.bean.WxPayApiData;
import com.rolbel.pay.bean.coupon.*;
import com.rolbel.pay.bean.notify.WxPayOrderNotifyResult;
import com.rolbel.pay.bean.notify.WxPayRefundNotifyResult;
import com.rolbel.pay.bean.notify.WxScanPayNotifyResult;
import com.rolbel.pay.bean.order.WxPayAppOrderResult;
import com.rolbel.pay.bean.order.WxPayMpOrderResult;
import com.rolbel.pay.bean.order.WxPayMwebOrderResult;
import com.rolbel.pay.bean.order.WxPayNativeOrderResult;
import com.rolbel.pay.bean.request.*;
import com.rolbel.pay.bean.result.*;
import com.rolbel.pay.config.WxPayConfig;
import com.rolbel.pay.constant.WxPayConstants;
import com.rolbel.pay.exception.WxPayException;
import com.rolbel.pay.util.SignUtils;
import jodd.io.ZipUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipException;

import static com.rolbel.pay.constant.WxPayConstants.QUERY_COMMENT_DATE_FORMAT;


/**
 * <pre>
 *  微信支付接口请求抽象实现类
 * </pre>
 */
public abstract class BaseWxPayServiceImpl implements WxPayService {
    private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";
    final Logger log = LoggerFactory.getLogger(this.getClass());
    static ThreadLocal<WxPayApiData> wxApiData = new ThreadLocal<>();

    private EntPayService entPayService = new EntPayServiceImpl(this);
    private PapPayService papPayService = new PapPayServiceImpl(this);

    private WxPayConfig config;

    @Override
    public EntPayService getEntPayService() {
        return entPayService;
    }

    @Override
    public void setEntPayService(EntPayService entPayService) {
        this.entPayService = entPayService;
    }

    @Override
    public PapPayService getPapPayService() {
        return papPayService;
    }

    @Override
    public void setPapPayService(PapPayService papPayService) {
        this.papPayService = papPayService;
    }

    @Override
    public WxPayConfig getConfig() {
        return this.config;
    }

    @Override
    public void setConfig(WxPayConfig config) {
        this.config = config;
    }

    @Override
    public String getPayBaseUrl() {
        if (this.getConfig().isUseSandboxEnv()) {
            return PAY_BASE_URL + "/sandboxnew";
        }

        return PAY_BASE_URL;
    }

    @Override
    public WxPayRefundResult refund(WxPayRefundRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/secapi/pay/refund";
        String responseContent = this.post(url, request.toXML(), true);
        WxPayRefundResult result = BaseWxPayResult.fromXML(responseContent, WxPayRefundResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayRefundQueryResult queryRefund(String transactionId, String outTradeNo, String outRefundNo, String refundId)
            throws WxPayException {
        WxPayRefundQueryRequest request = new WxPayRefundQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));
        request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
        request.setRefundId(StringUtils.trimToNull(refundId));

        return this.queryRefund(request);
    }

    @Override
    public WxPayRefundQueryResult queryRefund(WxPayRefundQueryRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/pay/refundquery";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayRefundQueryResult result = BaseWxPayResult.fromXML(responseContent, WxPayRefundQueryResult.class);
        result.composeRefundRecords();
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException {
        try {
            log.debug("微信支付异步通知请求参数：{}", xmlData);
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
            log.debug("微信支付异步通知请求解析后的对象：{}", result);
            result.checkResult(this, this.getConfig().getSignType(), false);
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
    public WxPayRefundNotifyResult parseRefundNotifyResult(String xmlData) throws WxPayException {
        try {
            log.debug("微信支付退款异步通知参数：{}", xmlData);
            WxPayRefundNotifyResult result = WxPayRefundNotifyResult.fromXML(xmlData, this.getConfig().getMchKey());
            log.debug("微信支付退款异步通知解析后的对象：{}", result);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WxPayException("发生异常，" + e.getMessage(), e);
        }
    }

    @Override
    public WxScanPayNotifyResult parseScanPayNotifyResult(String xmlData) throws WxPayException {
        try {
            log.debug("扫码支付回调通知请求参数：{}", xmlData);
            WxScanPayNotifyResult result = BaseWxPayResult.fromXML(xmlData, WxScanPayNotifyResult.class);
            log.debug("扫码支付回调通知解析后的对象：{}", result);
            result.checkResult(this, this.getConfig().getSignType(), false);
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
    public WxPaySendRedpackResult sendRedpack(WxPaySendRedpackRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/mmpaymkttransfers/sendredpack";
        if (request.getAmtType() != null) {
            //裂变红包
            url = this.getPayBaseUrl() + "/mmpaymkttransfers/sendgroupredpack";
        }

        String responseContent = this.post(url, request.toXML(), true);
        //无需校验，因为没有返回签名信息
        return BaseWxPayResult.fromXML(responseContent, WxPaySendRedpackResult.class);
    }

    @Override
    public WxPayRedpackQueryResult queryRedpack(String mchBillNo) throws WxPayException {
        WxPayRedpackQueryRequest request = new WxPayRedpackQueryRequest();
        request.setMchBillNo(mchBillNo);
        request.setBillType(WxPayConstants.BillType.MCHT);
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/mmpaymkttransfers/gethbinfo";
        String responseContent = this.post(url, request.toXML(), true);
        WxPayRedpackQueryResult result = BaseWxPayResult.fromXML(responseContent, WxPayRedpackQueryResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxPayException {
        WxPayOrderQueryRequest request = new WxPayOrderQueryRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
        request.setTransactionId(StringUtils.trimToNull(transactionId));

        return this.queryOrder(request);
    }

    @Override
    public WxPayOrderQueryResult queryOrder(WxPayOrderQueryRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/pay/orderquery";
        String responseContent = this.post(url, request.toXML(), false);
        if (StringUtils.isBlank(responseContent)) {
            throw new WxPayException("无响应结果");
        }

        WxPayOrderQueryResult result = BaseWxPayResult.fromXML(responseContent, WxPayOrderQueryResult.class);
        result.composeCoupons();
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayOrderCloseResult closeOrder(String outTradeNo) throws WxPayException {
        if (StringUtils.isBlank(outTradeNo)) {
            throw new WxPayException("out_trade_no不能为空");
        }

        WxPayOrderCloseRequest request = new WxPayOrderCloseRequest();
        request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));

        return this.closeOrder(request);
    }

    @Override
    public WxPayOrderCloseResult closeOrder(WxPayOrderCloseRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/pay/closeorder";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayOrderCloseResult result = BaseWxPayResult.fromXML(responseContent, WxPayOrderCloseResult.class);
        result.checkResult(this, request.getSignType(), true);

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createOrder(WxPayUnifiedOrderRequest request) throws WxPayException {
        WxPayUnifiedOrderResult unifiedOrderResult = this.unifiedOrder(request);
        String prepayId = unifiedOrderResult.getPrepayId();
        if (StringUtils.isBlank(prepayId)) {
            throw new WxPayException(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。",
                    unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
        }

        Long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = String.valueOf(System.currentTimeMillis());
        switch (request.getTradeType()) {
            case WxPayConstants.TradeType.MWEB: {
                return (T) new WxPayMwebOrderResult(unifiedOrderResult.getMwebUrl(), request.getOutTradeNo());
            }

            case WxPayConstants.TradeType.NATIVE: {
                return (T) WxPayNativeOrderResult.builder()
                        .codeUrl(unifiedOrderResult.getCodeURL())
                        .outTradeNo(request.getOutTradeNo())
                        .build();
            }

            case WxPayConstants.TradeType.APP: {
                // APP支付绑定的是微信开放平台上的账号，APPID为开放平台上绑定APP后发放的参数
                String appId = unifiedOrderResult.getAppId();
                if (StringUtils.isNotEmpty(unifiedOrderResult.getSubAppId())) {
                    appId = unifiedOrderResult.getSubAppId();
                }

                Map<String, String> configMap = new HashMap<>(8);
                // 此map用于参与调起sdk支付的二次签名,格式全小写，timestamp只能是10位,格式固定，切勿修改
                String partnerId;
                if (StringUtils.isEmpty(request.getMchId())) {
                    partnerId = this.getConfig().getMchId();
                } else {
                    partnerId = request.getMchId();
                }

                configMap.put("prepayid", prepayId);
                configMap.put("partnerid", partnerId);
                String packageValue = "Sign=WXPay";
                configMap.put("package", packageValue);
                configMap.put("timestamp", String.valueOf(timestamp));
                configMap.put("noncestr", nonceStr);
                configMap.put("appid", appId);

                final WxPayAppOrderResult result = WxPayAppOrderResult.builder()
                        .sign(SignUtils.createSign(configMap, null, this.getConfig().getMchKey(), false))
                        .prepayId(prepayId)
                        .partnerId(partnerId)
                        .appId(appId)
                        .packageValue(packageValue)
                        .timeStamp(timestamp)
                        .nonceStr(nonceStr)
                        .outTradeNo(request.getOutTradeNo())
                        .build();
                return (T) result;
            }

            case WxPayConstants.TradeType.JSAPI: {
                String signType = WxPayConstants.SignType.MD5;
                String appid = unifiedOrderResult.getAppId();
                if (StringUtils.isNotEmpty(unifiedOrderResult.getSubAppId())) {
                    appid = unifiedOrderResult.getSubAppId();
                }

                WxPayMpOrderResult payResult = WxPayMpOrderResult.builder()
                        .appId(appid)
                        .timeStamp(timestamp)
                        .nonceStr(nonceStr)
                        .packageValue("prepay_id=" + prepayId)
                        .signType(signType)
                        .build();

                payResult.setPaySign(SignUtils.createSign(payResult, signType, this.getConfig().getMchKey(), false));
                payResult.setOutTradeNo(request.getOutTradeNo());

                return (T) payResult;
            }

            default: {
                throw new WxPayException("该交易类型暂不支持");
            }
        }

    }

    @Override
    public WxPayUnifiedOrderResult unifiedOrder(WxPayUnifiedOrderRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/pay/unifiedorder";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayUnifiedOrderResult result = BaseWxPayResult.fromXML(responseContent, WxPayUnifiedOrderResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    @Deprecated
    public Map<String, String> getPayInfo(WxPayUnifiedOrderRequest request) throws WxPayException {
        WxPayUnifiedOrderResult unifiedOrderResult = this.unifiedOrder(request);
        String prepayId = unifiedOrderResult.getPrepayId();
        if (StringUtils.isBlank(prepayId)) {
            throw new WxPayException(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。",
                    unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
        }

        Map<String, String> payInfo = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = String.valueOf(System.currentTimeMillis());
        if (WxPayConstants.TradeType.NATIVE.equals(request.getTradeType())) {
            payInfo.put("codeUrl", unifiedOrderResult.getCodeURL());
        } else if (WxPayConstants.TradeType.APP.equals(request.getTradeType())) {
            // APP支付绑定的是微信开放平台上的账号，APPID为开放平台上绑定APP后发放的参数
            String appId = getConfig().getAppId();
            Map<String, String> configMap = new HashMap<>();
            // 此map用于参与调起sdk支付的二次签名,格式全小写，timestamp只能是10位,格式固定，切勿修改
            String partnerId = getConfig().getMchId();
            configMap.put("prepayid", prepayId);
            configMap.put("partnerid", partnerId);
            String packageValue = "Sign=WXPay";
            configMap.put("package", packageValue);
            configMap.put("timestamp", timestamp);
            configMap.put("noncestr", nonceStr);
            configMap.put("appid", appId);
            // 此map用于客户端与微信服务器交互
            payInfo.put("sign", SignUtils.createSign(configMap, null, this.getConfig().getMchKey(), false));
            payInfo.put("prepayId", prepayId);
            payInfo.put("partnerId", partnerId);
            payInfo.put("appId", appId);
            payInfo.put("packageValue", packageValue);
            payInfo.put("timeStamp", timestamp);
            payInfo.put("nonceStr", nonceStr);
        } else if (WxPayConstants.TradeType.JSAPI.equals(request.getTradeType())) {
            payInfo.put("appId", unifiedOrderResult.getAppId());
            // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
            payInfo.put("timeStamp", timestamp);
            payInfo.put("nonceStr", nonceStr);
            payInfo.put("package", "prepay_id=" + prepayId);
            payInfo.put("signType", WxPayConstants.SignType.MD5);
            payInfo.put("paySign", SignUtils.createSign(payInfo, null, this.getConfig().getMchKey(), false));
        }

        return payInfo;
    }

    @Override
    public String createScanPayQrcodeMode1(String productId) {
        //weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
        StringBuilder codeUrl = new StringBuilder("weixin://wxpay/bizpayurl?");
        Map<String, String> params = Maps.newHashMap();
        params.put("appid", this.getConfig().getAppId());
        params.put("mch_id", this.getConfig().getMchId());
        params.put("product_id", productId);
        params.put("time_stamp", String.valueOf(System.currentTimeMillis() / 1000));//这里需要秒，10位数字
        params.put("nonce_str", String.valueOf(System.currentTimeMillis()));

        String sign = SignUtils.createSign(params, null, this.getConfig().getMchKey(), false);
        params.put("sign", sign);

        for (String key : params.keySet()) {
            codeUrl.append(key).append("=").append(params.get(key)).append("&");
        }

        String content = codeUrl.toString().substring(0, codeUrl.length() - 1);
        log.debug("扫码支付模式一生成二维码的URL:{}", content);
        return content;
    }

    @Override
    public void report(WxPayReportRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/payitil/report";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayCommonResult result = BaseWxPayResult.fromXML(responseContent, WxPayCommonResult.class);
        result.checkResult(this, request.getSignType(), true);
    }

    @Override
    public WxPayBillResult downloadBill(String billDate, String billType, String tarType, String deviceInfo) throws WxPayException {
        if (!WxPayConstants.BillType.ALL.equals(billType)) {
            throw new WxPayException("目前仅支持ALL类型的对账单下载");
        }

        WxPayDownloadBillRequest request = new WxPayDownloadBillRequest();
        request.setBillType(billType);
        request.setBillDate(billDate);
        request.setTarType(tarType);
        request.setDeviceInfo(deviceInfo);

        return this.downloadBill(request);
    }

    @Override
    public WxPayBillResult downloadBill(WxPayDownloadBillRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/pay/downloadbill";

        String responseContent;
        if (WxPayConstants.TarType.GZIP.equals(request.getTarType())) {
            responseContent = this.handleGzipBill(url, request.toXML());
        } else {
            responseContent = this.post(url, request.toXML(), false);
            if (responseContent.startsWith("<")) {
                throw WxPayException.from(BaseWxPayResult.fromXML(responseContent, WxPayCommonResult.class));
            }
        }

        return this.handleBill(request.getBillType(), responseContent);
    }

    private WxPayBillResult handleBill(String billType, String responseContent) {
        if (!WxPayConstants.BillType.ALL.equals(billType)) {
            return null;
        }

        return this.handleAllBill(responseContent);
    }

    private String handleGzipBill(String url, String requestStr) throws WxPayException {
        try {
            byte[] responseBytes = this.postForBytes(url, requestStr, false);
            Path tempDirectory = Files.createTempDirectory("bill");
            Path path = Paths.get(tempDirectory.toString(), System.currentTimeMillis() + ".gzip");
            Files.write(path, responseBytes);
            try {
                List<String> allLines = Files.readAllLines(ZipUtil.ungzip(path.toFile()).toPath(), StandardCharsets.UTF_8);
                return Joiner.on("\n").join(allLines);
            } catch (ZipException e) {
                if (e.getMessage().contains("Not in GZIP format")) {
                    throw WxPayException.from(BaseWxPayResult.fromXML(new String(responseBytes, StandardCharsets.UTF_8),
                            WxPayCommonResult.class));
                } else {
                    this.log.error("解压zip文件出错", e);
                }
            }
        } catch (Exception e) {
            this.log.error("解析对账单文件时出错", e);
        }
        return null;
    }

    private WxPayBillResult handleAllBill(String responseContent) {
        WxPayBillResult wxPayBillResult = new WxPayBillResult();

        String listStr = "";
        String objStr = "";
        if (responseContent.contains("总交易单数")) {
            listStr = responseContent.substring(0, responseContent.indexOf("总交易单数"));
            objStr = responseContent.substring(responseContent.indexOf("总交易单数"));
        }

        /*
         * 交易时间:2017-04-06 01:00:02 公众账号ID: 商户号: 子商户号:0 设备号:WEB 微信订单号: 商户订单号:2017040519091071873216 用户标识: 交易类型:NATIVE
         * 交易状态:REFUND 付款银行:CFT 货币种类:CNY 总金额:0.00 企业红包金额:0.00 微信退款单号: 商户退款单号:20170406010000933 退款金额:0.01 企业红包退款金额:0.00
         * 退款类型:ORIGINAL 退款状态:SUCCESS 商品名称: 商户数据包: 手续费:0.00000 费率 :0.60%
         * 参考以上格式进行取值
         */
        List<WxPayBillBaseResult> wxPayBillBaseResultLst = new LinkedList<>();
        // 去空格
        String newStr = listStr.replaceAll(",", " ");
        // 数据分组
        String[] tempStr = newStr.split("`");
        // 分组标题
        String[] t = tempStr[0].split(" ");
        // 计算循环次数
        int j = tempStr.length / t.length;
        // 纪录数组下标
        int k = 1;
        for (int i = 0; i < j; i++) {
            WxPayBillBaseResult wxPayBillBaseResult = new WxPayBillBaseResult();

            wxPayBillBaseResult.setTradeTime(tempStr[k].trim());
            wxPayBillBaseResult.setAppId(tempStr[k + 1].trim());
            wxPayBillBaseResult.setMchId(tempStr[k + 2].trim());
            wxPayBillBaseResult.setSubMchId(tempStr[k + 3].trim());
            wxPayBillBaseResult.setDeviceInfo(tempStr[k + 4].trim());
            wxPayBillBaseResult.setTransactionId(tempStr[k + 5].trim());
            wxPayBillBaseResult.setOutTradeNo(tempStr[k + 6].trim());
            wxPayBillBaseResult.setOpenId(tempStr[k + 7].trim());
            wxPayBillBaseResult.setTradeType(tempStr[k + 8].trim());
            wxPayBillBaseResult.setTradeState(tempStr[k + 9].trim());
            wxPayBillBaseResult.setBankType(tempStr[k + 10].trim());
            wxPayBillBaseResult.setFeeType(tempStr[k + 11].trim());
            wxPayBillBaseResult.setTotalFee(tempStr[k + 12].trim());
            wxPayBillBaseResult.setCouponFee(tempStr[k + 13].trim());
            wxPayBillBaseResult.setRefundId(tempStr[k + 14].trim());
            wxPayBillBaseResult.setOutRefundNo(tempStr[k + 15].trim());
            wxPayBillBaseResult.setSettlementRefundFee(tempStr[k + 16].trim());
            wxPayBillBaseResult.setCouponRefundFee(tempStr[k + 17].trim());
            wxPayBillBaseResult.setRefundChannel(tempStr[k + 18].trim());
            wxPayBillBaseResult.setRefundState(tempStr[k + 19].trim());
            wxPayBillBaseResult.setBody(tempStr[k + 20].trim());
            wxPayBillBaseResult.setAttach(tempStr[k + 21].trim());
            wxPayBillBaseResult.setPoundage(tempStr[k + 22].trim());
            wxPayBillBaseResult.setPoundageRate(tempStr[k + 23].trim());
            wxPayBillBaseResultLst.add(wxPayBillBaseResult);
            k += t.length;
        }
        wxPayBillResult.setWxPayBillBaseResultLst(wxPayBillBaseResultLst);

        /*
         * 总交易单数,总交易额,总退款金额,总代金券或立减优惠退款金额,手续费总金额 `2,`0.02,`0.0,`0.0,`0
         * 参考以上格式进行取值
         */
        String totalStr = objStr.replaceAll(",", " ");
        String[] totalTempStr = totalStr.split("`");
        wxPayBillResult.setTotalRecord(totalTempStr[1]);
        wxPayBillResult.setTotalFee(totalTempStr[2]);
        wxPayBillResult.setTotalRefundFee(totalTempStr[3]);
        wxPayBillResult.setTotalCouponFee(totalTempStr[4]);
        wxPayBillResult.setTotalPoundageFee(totalTempStr[5]);

        return wxPayBillResult;
    }

    @Override
    public WxPayMicropayResult micropay(WxPayMicropayRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/pay/micropay";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayMicropayResult result = BaseWxPayResult.fromXML(responseContent, WxPayMicropayResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayOrderReverseResult reverseOrder(WxPayOrderReverseRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/secapi/pay/reverse";
        String responseContent = this.post(url, request.toXML(), true);
        WxPayOrderReverseResult result = BaseWxPayResult.fromXML(responseContent, WxPayOrderReverseResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public String shorturl(WxPayShorturlRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/tools/shorturl";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayShorturlResult result = BaseWxPayResult.fromXML(responseContent, WxPayShorturlResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result.getShortUrl();
    }

    @Override
    public String shorturl(String longUrl) throws WxPayException {
        return this.shorturl(new WxPayShorturlRequest(longUrl));
    }

    @Override
    public String authCode2OpenId(WxPayAuthCode2OpenIdRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/tools/authcodetoopenid";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayAuthCode2OpenIdResult result = BaseWxPayResult.fromXML(responseContent, WxPayAuthCode2OpenIdResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result.getOpenId();
    }

    @Override
    public String authCode2OpenId(String authCode) throws WxPayException {
        return this.authCode2OpenId(new WxPayAuthCode2OpenIdRequest(authCode));
    }

    @Override
    public String getSandboxSignKey() throws WxPayException {
        WxPayDefaultRequest request = new WxPayDefaultRequest();
        request.checkAndSign(this.getConfig());

        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        String responseContent = this.post(url, request.toXML(), false);
        WxPaySandboxSignKeyResult result = BaseWxPayResult.fromXML(responseContent, WxPaySandboxSignKeyResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result.getSandboxSignKey();
    }

    @Override
    public WxPayCouponSendResult sendCoupon(WxPayCouponSendRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/mmpaymkttransfers/send_coupon";
        String responseContent = this.post(url, request.toXML(), true);
        WxPayCouponSendResult result = BaseWxPayResult.fromXML(responseContent, WxPayCouponSendResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayCouponStockQueryResult queryCouponStock(WxPayCouponStockQueryRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/mmpaymkttransfers/query_coupon_stock";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayCouponStockQueryResult result = BaseWxPayResult.fromXML(responseContent, WxPayCouponStockQueryResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayCouponInfoQueryResult queryCouponInfo(WxPayCouponInfoQueryRequest request) throws WxPayException {
        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/mmpaymkttransfers/querycouponsinfo";
        String responseContent = this.post(url, request.toXML(), false);
        WxPayCouponInfoQueryResult result = BaseWxPayResult.fromXML(responseContent, WxPayCouponInfoQueryResult.class);
        result.checkResult(this, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayApiData getWxApiData() {
        try {
            return wxApiData.get();
        } finally {
            //一般来说，接口请求会在一个线程内进行，这种情况下，每个线程get的会是之前所存入的数据，
            // 但以防万一有同一线程多次请求的问题，所以每次获取完数据后移除对应数据
            wxApiData.remove();
        }
    }

    @Override
    public String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws WxPayException {
        WxPayQueryCommentRequest request = new WxPayQueryCommentRequest();
        request.setBeginTime(QUERY_COMMENT_DATE_FORMAT.format(beginDate));
        request.setEndTime(QUERY_COMMENT_DATE_FORMAT.format(endDate));
        request.setOffset(offset);
        request.setLimit(limit);
        request.setSignType(WxPayConstants.SignType.HMAC_SHA256);

        request.checkAndSign(this.getConfig());

        String url = this.getPayBaseUrl() + "/billcommentsp/batchquerycomment";

        String responseContent = this.post(url, request.toXML(), true);
        if (responseContent.startsWith("<")) {
            throw WxPayException.from(BaseWxPayResult.fromXML(responseContent, WxPayCommonResult.class));
        }

        return responseContent;
    }
}
