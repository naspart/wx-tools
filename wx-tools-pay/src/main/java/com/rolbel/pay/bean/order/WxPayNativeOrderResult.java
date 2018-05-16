package com.rolbel.pay.bean.order;

import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * 微信扫码支付统一下单后发起支付拼接所需参数实现类
 * </pre>
 */
@Data
@Builder
public class WxPayNativeOrderResult {
    private String codeUrl;
}
