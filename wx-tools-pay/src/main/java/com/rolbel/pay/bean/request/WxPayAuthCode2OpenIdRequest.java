package com.rolbel.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

/**
 * <pre>
 * 授权码查询openid接口请求对象类
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayAuthCode2OpenIdRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -6624371203934675859L;
    
    /**
     * <pre>
     *     授权码
     *     auth_code
     *     是
     *     String(128)
     *     扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * </pre>
     */
    @XStreamAlias("auth_code")
    private String authCode;

    @Override
    protected void checkConstraints() {
        // nothing to do
    }
}
