package com.naspat.pay.bean.pappay.request;

import com.naspat.pay.bean.request.BaseWxPayRequest;
import com.naspat.pay.exception.WxPayException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayAppSignRequest extends BaseWxPayRequest {

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

    @XStreamAlias("return_app")
    private Integer returnApp;

    @Override
    protected boolean ignoreNonceStr() {
        return true;
    }

    @Override
    protected void checkConstraints() throws WxPayException {

    }

    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"sign_type"};
    }
}
