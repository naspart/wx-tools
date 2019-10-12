package com.naspat.pay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 关闭订单结果对象类
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderCloseResult extends BaseWxPayResult {
    private static final long serialVersionUID = 2316872039394065737L;

    /**
     * 业务结果描述
     */
    @XStreamAlias("result_msg")
    private String resultMsg;
}
