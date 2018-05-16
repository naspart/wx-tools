package com.rolbel.pay.bean.result;

import com.rolbel.pay.bean.entpay.EntPayQueryResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.beanutils.BeanUtils;

/**
 * 企业付款查询返回结果
 * 请使用{@link EntPayQueryResult}
 */
@XStreamAlias("xml")
@Deprecated
public class WxEntPayQueryResult extends EntPayQueryResult {
    public static WxEntPayQueryResult createFrom(EntPayQueryResult entPayQueryResult) {
        WxEntPayQueryResult result = new WxEntPayQueryResult();
        try {
            BeanUtils.copyProperties(result, entPayQueryResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
