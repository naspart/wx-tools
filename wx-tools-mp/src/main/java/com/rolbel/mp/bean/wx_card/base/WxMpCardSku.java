package com.rolbel.mp.bean.wx_card.base;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.annotation.Required;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpCardSku implements Serializable {
    private static final long serialVersionUID = -5565257184656099618L;

    /**
     * 卡券库存的数量，上限为100000000
     */
    @Required
    @SerializedName("quantity")
    private Integer quantity;
}
