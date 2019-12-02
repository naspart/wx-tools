package com.naspat.pay.bean.pappay.result;

import com.naspat.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PayPayAppSignResult extends BaseWxPayResult {
    @XStreamAlias("pre_entrustweb_id")
    private String preEntrustwebId;
}
