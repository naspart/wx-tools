package com.naspat.mp.bean.data_cube;

import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  统计接口的共用属性类.
 * </pre>
 */
@Data
public abstract class WxMpDataCubeBaseResult implements Serializable {
    private static final long serialVersionUID = -7819479233765685788L;
    protected static final JsonParser JSON_PARSER = new JsonParser();

    /**
     * ref_date.
     * 数据的日期，需在begin_date和end_date之间
     */
    @SerializedName("ref_date")
    private String refDate;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
