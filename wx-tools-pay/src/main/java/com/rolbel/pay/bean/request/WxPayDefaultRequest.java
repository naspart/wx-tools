package com.rolbel.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 *  支付请求默认对象类
 * </pre>
 */
@XStreamAlias("xml")
public class WxPayDefaultRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 1492497380324798949L;

    @Override
    protected void checkConstraints() {
        //do nothing
    }
}
