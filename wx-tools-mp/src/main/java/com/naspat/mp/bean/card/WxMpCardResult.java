package com.naspat.mp.bean.card;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡券查询Code，核销Code接口返回结果
 */
@Data
public class WxMpCardResult implements Serializable {
    private static final long serialVersionUID = -2267344121514381008L;

    private String errorCode;

    private String errorMsg;

    private String openId;

    private WxMpCard card;

    private String userCardStatus;

    private Boolean canConsume;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
