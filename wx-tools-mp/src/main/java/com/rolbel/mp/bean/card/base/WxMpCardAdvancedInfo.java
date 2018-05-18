package com.rolbel.mp.bean.card.base;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class WxMpCardAdvancedInfo implements Serializable {
    private static final long serialVersionUID = 607224853342225387L;

    /**
     * 使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
     */
    @SerializedName("use_condition")
    private WxMpCardUseCondition userCondition;

    /**
     * 封面摘要结构体名称
     */
    @SerializedName("abstract")
    private WxMpCardAbstract abstractInfo;

    /**
     * 图文列表，显示在详情内页 ，优惠券券开发者须至少传入 一组图文列表
     */
    @SerializedName("text_image_list")
    private List<WxMpCardTextImage> textImageList;

    /**
     * 使用时段限制
     */
    @SerializedName("time_limit")
    private List<WxMpCardTimeLimit> timeLimit;

    /**
     * 商家服务类型： BIZ_SERVICE_DELIVER 外卖服务； BIZ_SERVICE_FREE_PARK 停车位； BIZ_SERVICE_WITH_PET 可带宠物； BIZ_SERVICE_FREE_WIFI 免费wifi， 可多选
     */
    private List<String> businessService;
}
