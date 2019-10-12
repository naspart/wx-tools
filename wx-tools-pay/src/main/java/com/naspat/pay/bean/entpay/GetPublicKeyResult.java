package com.naspat.pay.bean.entpay;

import com.naspat.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 *  企业付款获取RSA加密公钥接口返回结果类
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XStreamAlias("xml")
public class GetPublicKeyResult extends BaseWxPayResult {
    private static final long serialVersionUID = -5372894002871178374L;

    /**
     * 商户号.
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 密钥
     */
    @XStreamAlias("pub_key")
    private String pubKey;
}
