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
public class PapPayApplyPayResult extends BaseWxPayResult {
    private static final long serialVersionUID = 6385671349925544795L;
}
