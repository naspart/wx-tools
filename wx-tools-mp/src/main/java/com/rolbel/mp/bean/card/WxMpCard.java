package com.rolbel.mp.bean.card;


import com.rolbel.common.util.ToStringUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信卡券
 */
@Data
public class WxMpCard implements Serializable {
    private static final long serialVersionUID = 9214301870017772921L;

    private String cardId;

    private Long beginTime;

    private Long endTime;

    private String userCardStatus;

    private Boolean canConsume;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
