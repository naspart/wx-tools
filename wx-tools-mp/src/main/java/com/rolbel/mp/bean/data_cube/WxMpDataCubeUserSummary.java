package com.rolbel.mp.bean.data_cube;

import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.util.ToStringUtil;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 用户增减数据接口的返回JSON数据包
 * 详情查看文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析数据接口</a>
 * </pre>
 */
@Data
public class WxMpDataCubeUserSummary implements Serializable {
    private static final long serialVersionUID = -2336654489906694173L;

    private static final JsonParser JSON_PARSER = new JsonParser();

    private Date refDate;

    private Integer userSource;

    private Integer newUser;

    private Integer cancelUser;

    public static List<WxMpDataCubeUserSummary> fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                JSON_PARSER.parse(json).getAsJsonObject().get("list"),
                new TypeToken<List<WxMpDataCubeUserSummary>>() {
                }.getType());
    }

    @Override
    public String toString() {
        return ToStringUtil.toSimpleString(this);
    }
}
