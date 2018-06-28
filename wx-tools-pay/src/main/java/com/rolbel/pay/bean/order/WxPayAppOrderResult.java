package com.rolbel.pay.bean.order;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * APP支付调用统一下单接口后的组装所需参数的实现类
 * 参考 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
 * </pre>
 */
@Data
@Builder
public class WxPayAppOrderResult implements Serializable {
    private static final long serialVersionUID = -858011633066143129L;

    private String sign;
    private String prepayId;
    private String partnerId;
    private String appId;
    private String packageValue;
    private Long timeStamp;
    private String nonceStr;
    private String outTradeNo;
}
