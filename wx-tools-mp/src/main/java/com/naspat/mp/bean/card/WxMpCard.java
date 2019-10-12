package com.naspat.mp.bean.card;


import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信卡券.
 */
@Data
public class WxMpCard implements Serializable {
    private static final long serialVersionUID = 7500848463262251235L;

    private String cardId;

    private Long beginTime;

    private Long endTime;

    private String userCardStatus;

    private Boolean canConsume;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
