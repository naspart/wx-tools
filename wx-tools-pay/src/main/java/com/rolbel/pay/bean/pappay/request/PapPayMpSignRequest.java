package com.rolbel.pay.bean.pappay.request;

import com.rolbel.pay.bean.request.BaseWxPayRequest;
import com.rolbel.pay.config.WxPayConfig;
import com.rolbel.pay.exception.WxPayException;
import com.rolbel.pay.util.SignUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.rolbel.pay.constant.WxPayConstants.SignType.ALL_SIGN_TYPES;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayMpSignRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -3943692497911648948L;

    @XStreamAlias("plan_id")
    protected String planId;

    @XStreamAlias("contract_code")
    private String contractCode;

    @XStreamAlias("request_serial")
    private Long requestSerial;

    @XStreamAlias("contract_display_account")
    private String contractDisplayAccount;

    @XStreamAlias("notify_url")
    private String notifyUrl;

    @XStreamAlias("version")
    private String version;

    @XStreamAlias("timestamp")
    private String timestamp;

    @XStreamAlias("clientip")
    private String clientIp;

    @XStreamAlias("deviceid")
    private String deviceId;

    @XStreamAlias("mobile")
    private String mobile;

    @XStreamAlias("email")
    private String email;

    @XStreamAlias("qq")
    private String qq;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("creid")
    private String creId;

    @XStreamAlias("outerid")
    private String outerId;

    @XStreamAlias("return_app")
    private Integer returnApp;

    @XStreamAlias("return_web")
    private Integer returnWeb;

    @Override
    protected void checkConstraints() throws WxPayException {

    }

    @Override
    public void checkAndSign(WxPayConfig config) throws WxPayException {
        if (!ignoreAppid()) {
            if (StringUtils.isBlank(getAppId())) {
                this.setAppId(config.getAppId());
            }
        }

        if (StringUtils.isBlank(getMchId())) {
            this.setMchId(config.getMchId());
        }

        if (StringUtils.isBlank(getSignType())) {
            if (config.getSignType() != null && !ALL_SIGN_TYPES.contains(config.getSignType())) {
                throw new WxPayException("非法的signType配置：" + config.getSignType() + "，请检查配置！");
            }
            this.setSignType(StringUtils.trimToNull(config.getSignType()));
        } else {
            if (!ALL_SIGN_TYPES.contains(this.getSignType())) {
                throw new WxPayException("非法的sign_type参数：" + this.getSignType());
            }
        }

        this.setNonceStr(null);     //免密支付签约参数中不需要 nonce_str 字段

        //设置签名字段的值
        this.setSign(SignUtils.createSign(this, this.getSignType(), config.getMchKey(), true));
    }
}
