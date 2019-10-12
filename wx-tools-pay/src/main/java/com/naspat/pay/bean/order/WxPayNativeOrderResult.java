package com.naspat.pay.bean.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 微信扫码支付统一下单后发起支付拼接所需参数实现类
 * </pre>
 */
@Data
@Builder
@AllArgsConstructor
public class WxPayNativeOrderResult implements Serializable {
    private static final long serialVersionUID = 4491622973581226596L;

    private String codeUrl;
    private String outTradeNo;
}
