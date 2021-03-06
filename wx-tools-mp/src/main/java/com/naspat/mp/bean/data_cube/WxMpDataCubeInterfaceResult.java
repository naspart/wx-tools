package com.naspat.mp.bean.data_cube;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 接口分析数据接口返回结果对象
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpDataCubeInterfaceResult extends WxMpDataCubeBaseResult {
    private static final long serialVersionUID = 5712867454069684238L;

    /**
     * ref_hour
     * 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
     */
    @SerializedName("ref_hour")
    private Integer refHour;

    /**
     * callback_count
     * 通过服务器配置地址获得消息后，被动回复用户消息的次数
     */
    @SerializedName("callback_count")
    private Integer callbackCount;

    /**
     * fail_count
     * 上述动作的失败次数
     */
    @SerializedName("fail_count")
    private Integer failCount;

    /**
     * total_time_cost
     * 总耗时，除以callback_count即为平均耗时
     */
    @SerializedName("total_time_cost")
    private Integer totalTimeCost;

    /**
     * max_time_cost
     * 最大耗时
     */
    @SerializedName("max_time_cost")
    private Integer maxTimeCost;

    public static List<WxMpDataCubeInterfaceResult> fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(
                JSON_PARSER.parse(json).getAsJsonObject().get("list"),
                new TypeToken<List<WxMpDataCubeInterfaceResult>>() {
                }.getType());
    }
}
