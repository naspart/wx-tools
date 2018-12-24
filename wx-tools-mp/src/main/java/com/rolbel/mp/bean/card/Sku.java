package com.rolbel.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品信息.
 */
@Data
public class Sku implements Serializable {
    /**
     * 卡券库存的数量,不支持填写0，上限为100000000。
     */
    @SerializedName("quantity")
    private Integer quantity = 100000000;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
