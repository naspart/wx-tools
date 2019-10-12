package com.naspat.pay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPaySandboxSignKeyResult extends BaseWxPayResult {
    private static final long serialVersionUID = -487000235165851593L;

    /**
     * <pre>
     * 沙箱密钥
     * sandbox_signkey
     * 否
     * 013467007045764
     * String(32)
     * 返回的沙箱密钥
     * </pre>
     */
    @XStreamAlias("sandbox_signkey")
    private String sandboxSignKey;
}
