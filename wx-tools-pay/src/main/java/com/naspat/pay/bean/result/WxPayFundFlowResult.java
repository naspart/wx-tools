package com.naspat.pay.bean.result;

import com.naspat.common.util.json.WxGsonBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 下载资金账单接口响应结果对象类
 * </pre>
 */
@Data
@NoArgsConstructor
public class WxPayFundFlowResult implements Serializable {
    private static final long serialVersionUID = -5713019103538919698L;

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

    /**
     * 资金流水返回对象
     */
    private List<WxPayFundFlowBaseResult> wxPayFundFlowBaseResultList;

    /**
     * 资金流水总笔数
     */
    private String totalRecord;

    /**
     * 收入笔数
     */
    private String incomeRecord;

    /**
     * 收入金额
     */
    private String incomeAmount;

    /**
     * 支出笔数
     */
    private String expenditureRecord;

    /**
     * 支出金额
     */
    private String expenditureAmount;

}
