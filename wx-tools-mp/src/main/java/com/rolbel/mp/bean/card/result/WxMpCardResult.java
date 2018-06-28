package com.rolbel.mp.bean.card.result;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.bean.card.WxMpCardInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡券查询Code，核销Code接口返回结果
 */
@Data
public class WxMpCardResult implements Serializable {
    private static final long serialVersionUID = 2501693380337540826L;

    private String openId;

    private WxMpCardInfo card;

    private String userCardStatus;

    private Boolean canConsume;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
