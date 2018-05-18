package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WxMpCardUseCondition {
    private static final long serialVersionUID = 5356177306278854046L;

    /**
     * 指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写适用于xxx
     */
    @SerializedName("accept_category")
    private String acceptCategory;

    /**
     * 指定不可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写不适用于xxxx
     */
    @SerializedName("reject_category")
    private String rejectCategory;

    /**
     * 满减门槛字段，可用于兑换券和代金券 ，填入后将在全面拼写消费满xx元可用。
     */
    @SerializedName("least_cost")
    private Integer leastCost;

    /**
     * 购买xx可用类型门槛，仅用于兑换 ，填入后自动拼写购买xxx可用。
     */
    @SerializedName("object_use_for")
    private String objectUseFor;

    /**
     * 不可以与其他类型共享门槛 ，填写false时系统将在使用须知里 拼写“不可与其他优惠共享”， 填写true时系统将在使用须知里 拼写“可与其他优惠共享”， 默认为true
     */
    @SerializedName("can_use_with_other_discount")
    private Boolean canUseWithOtherDiscount;
}
