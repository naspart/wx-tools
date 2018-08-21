package com.rolbel.pay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  授权码查询openid接口请求结果类
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayAuthCode2OpenIdResult extends BaseWxPayResult {
    private static final long serialVersionUID = 1394395337224725516L;
    
    /**
     * <pre>
     *   用户标识.
     *   openid
     *   是
     *   String(128)
     *   用户在商户appid下的唯一标识
     * </pre>
     */
    @XStreamAlias("openid")
    private String openId;

}
