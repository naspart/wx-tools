package com.naspat.pay.api;

import com.naspat.pay.bean.pappay.request.*;
import com.naspat.pay.bean.pappay.result.*;
import com.naspat.pay.exception.WxPayException;

public interface PapPayService {
    /**
     * <pre>
     * 公众号/APP纯签约API.
     * 商户可以通过请求此接口唤起微信委托代扣的页面。
     * 用户在微信的页面中完成代扣签约后，微信会同时将签约信息通过异步通知的方式通知给商户后台。如果用户放弃签约或签约失败则不通知。
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_1&index=1
     * 接口链接：https://api.mch.weixin.qq.com/papay/entrustweb
     * </pre>
     *
     * @param request 请求对象
     */
    String getMpSignUrl(PapPayMpSignRequest request) throws WxPayException;

    /**
     * <pre>
     * 支付中签约API.
     * 支付的同时完成代扣协议的签约。
     * 1.请求支付中签约接口（接口请求参数见下文）,获取预支付id （对应参数：prepay_id）. 此步骤需要根据不同支付方式选择不同的trade_type；
     * 2.按照不同支付方式的不同规则,按要求唤起微信支付收银台.不同的支付方式,对于唤起微信支付收银台的方法要求不同；
     * 3.用户完成支付,微信通过支付中签约接口中商户上传的通知回调地址（对应参数：notify_url）,将支付结果返回给商户,同时将签约结果通过contract_notify_url通知给商户,两次通知皆为异步通知；
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_13&index=4
     * 接口链接：https://api.mch.weixin.qq.com/pay/contractorder
     * </pre>
     *
     * @param request 请求对象
     */
    PapPayPayAndSignResult payAndSign(PapPayPayAndSignRequest request) throws WxPayException;

    /**
     * <pre>
     * 解析签约、解约结果通知.
     * 签约、解约成功后（不成功不通知），微信会把相关签约、解约信息异步通知给商户。商户在收到结果通知后，需马上应答。
     * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知,尽可能提高通知的成功率，但微信不保证通知最终能成功。
     * （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
     * 由于存在重新发送后台通知的情况，因此同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     * 签约结果通知路径为签约接口商户上传的notify_url字段，解约结果通知路径为商户配置委托扣款模版ID时填写的解约回调地址
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_17&index=5
     * 接口链接：https://api.mch.weixin.qq.com/papay/entrustweb
     *         https://api.mch.weixin.qq.com/pay/contractorder
     * </pre>
     *
     * @param xmlData 请求对象
     */
    PapPaySignNotifyResult parseSignNotifyResult(String xmlData) throws WxPayException;

    /**
     * <pre>
     * 查询签约关系API.
     * 查询签约关系接口提供单笔签约关系查询：
     * 查询方式一：使用微信返回的委托代扣协议contract_id进行查询
     * 查询方式二：plan_id + contract_code模式：传入模板id和委托代扣协议号进行查询
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_2&index=6
     * 接口链接：https://api.mch.weixin.qq.com/papay/querycontract
     * </pre>
     *
     * @param request 请求对象
     */
    PapPayContractQueryResult queryContract(PapPayContractQueryRequest request) throws WxPayException;

    /**
     * <pre>
     * 申请扣款API.
     * 委托代扣可应用于定期扣款或需事后扣款以期提高效率的场景。例如但不限于，会员制缴费、水电煤缴费、黄钻绿钻增值服务、打车类软件、停车场或高速公路无人缴费、理财通基金定投、信用卡还款等通过用户授权给商户，进行委托扣款的场景。
     * 注：扣费请求首先按签约协议中记录的优先支付方式扣费，否则依次按以下顺序扣费：零钱、信用卡、借记卡
     * 注意：对于自动续费的代扣申请，微信将延迟24小时进行扣款（首次签约可在签约后12小时内立即扣款成功，不受此规则限制）
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_3&index=7
     * 接口链接：https://api.mch.weixin.qq.com/pay/pappayapply
     * </pre>
     *
     * @param request 请求对象
     */
    PapPayApplyPayResult applyPay(PapPayApplyPayRequest request) throws WxPayException;

    /**
     * <pre>
     * 解析支付结果通知.
     * 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
     *
     * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，尽可能提高通知的成功率，但微信不保证通知最终能成功。 （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
     *
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     *
     * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_7&index=10
     * 接口链接：https://api.mch.weixin.qq.com/pay/pappayapply
     * </pre>
     *
     * @param xmlData 请求对象
     */
    PapPayOrderNotifyResult parseApplyPayNotifyResult(String xmlData) throws WxPayException;

    /**
     * <pre>
     * 申请解约API.
     * 商户与用户的签约关系有误或者商户主动要求与用户之前的签约协议时可调用此接口完成解约。目前商户侧支持两种模式的解约：
     * 1.contract_id模式：使用委托代扣协议id完成解约
     * 2.plan_id + contract_code模式：使用模板id和委托代扣协议号完成解约
     *
     * 商户可以在商户后台（pay.weixin.qq.com）设置解约回调地址，当发生解约关系的时候，微信服务器会向此地址通知解约信息，内容与签约返回一致（详见签约返回）。
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_4&index=8
     * 接口链接：https://api.mch.weixin.qq.com/papay/deletecontract
     * </pre>
     *
     * @param request 请求对象
     */
    PapPayUnsignResult unsign(PapPayUnsignRequest request) throws WxPayException;

    /**
     * <pre>
     * 查询订单API.
     * 该接口仅提供微信代扣订单的查询，商户可以通过该接口主动查询微信代扣订单状态，完成下一步的业务逻辑。
     *
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_10&index=13
     * 接口链接：https://api.mch.weixin.qq.com/pay/paporderquery
     * </pre>
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     */
    PapPayOrderQueryResult queryOrder(String transactionId, String outTradeNo) throws WxPayException;

    /**
     * <pre>
     * 查询订单API.
     * 该接口仅提供微信代扣订单的查询，商户可以通过该接口主动查询微信代扣订单状态，完成下一步的业务逻辑。
     *
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_10&index=13
     * 接口链接：https://api.mch.weixin.qq.com/pay/paporderquery
     * </pre>
     *
     * @param request 请求对象
     */
    PapPayOrderQueryResult queryOrder(PapPayOrderQueryRequest request) throws WxPayException;

    /**
     * <pre>
     * 微信支付-申请退款.
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_8&index=11
     * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
     * </pre>
     *
     * @param request 请求对象
     * @return 退款操作结果
     */
    PapPayRefundResult refund(PapPayRefundRequest request) throws WxPayException;

    /**
     * <pre>
     * 查询退款API.
     * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_9&index=12
     * 接口链接：https://api.mch.weixin.qq.com/pay/paporderquery
     * </pre>
     *
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param refundId      微信退款单号
     */
    PapPayRefundQueryResult queryRefund(String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxPayException;

    /**
     * <pre>
     * 查询退款API.
     * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
     *
     * 文档详见：https://pay.weixin.qq.com/wiki/doc/api/pap.php?chapter=18_9&index=12
     * 接口链接：https://api.mch.weixin.qq.com/pay/paporderquery
     * </pre>
     *
     * @param request 请求对象
     */
    PapPayRefundQueryResult queryRefund(PapPayRefundQueryRequest request) throws WxPayException;
}
