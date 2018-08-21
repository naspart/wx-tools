package com.rolbel.pay.bean.pappay.result;

import com.rolbel.pay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PapPayApplyPayResult extends BaseWxPayResult {
    private static final long serialVersionUID = 8235580100634321232L;
}
