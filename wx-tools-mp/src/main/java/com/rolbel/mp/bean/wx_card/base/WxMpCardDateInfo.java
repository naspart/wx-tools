package com.rolbel.mp.bean.wx_card.base;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.annotation.Required;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 使用日期，有效期的信息
 */
@Data
@Builder
public class WxMpCardDateInfo implements Serializable {
    private static final long serialVersionUID = 8859736181595225997L;

    /**
     * 使用时间的类型，旧文档采用的1和2依然生效
     */
    @Required
    @SerializedName("type")
    private String type;

    /**
     * type为DATE_TYPE_FIX_TIME_RANGE时专用，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数，最终需转换为字符串形态传入。
     */
    @SerializedName("begin_timestamp")
    private Long beginTimestamp;

    /**
     * 表示结束时间 ， 建议设置为截止日期的23:59:59过期 。 （ 东八区时间,UTC+8，单位为秒 ）
     */
    @SerializedName("end_timestamp")
    private Long endTimestamp;

    /**
     * type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天内有效，不支持填写0。
     */
    @SerializedName("fixed_term")
    private Integer fixedTerm;

    /**
     * type为DATE_TYPE_FIX_TERM时专用，表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）
     */
    @SerializedName("fixed_begin_term")
    private Integer fixedBeginTerm;
}
