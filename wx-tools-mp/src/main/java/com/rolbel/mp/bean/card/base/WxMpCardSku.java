package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WxMpCardSku {
    private static final long serialVersionUID = 5035426573467141853L;

    /**
     * 卡券库存的数量，上限为100000000
     */
    @Required
    @SerializedName("quantity")
    private Integer quantity;
}
