package com.rolbel.pay.bean.papay;

import com.rolbel.pay.bean.request.BaseWxPayRequest;
import com.rolbel.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayMpSignRequest extends BaseWxPayRequest {
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
}
