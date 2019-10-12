package com.naspat.mp.bean.data_cube;

import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 累计用户数据接口的返回JSON数据包
 * 详情查看文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析数据接口</a>
 * </pre>
 */
@Data
public class WxMpDataCubeUserCumulate implements Serializable {
    private static final long serialVersionUID = 5913425081302433811L;

    private static final JsonParser JSON_PARSER = new JsonParser();

    private Date refDate;

    private Integer cumulateUser;

    public static List<WxMpDataCubeUserCumulate> fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(
                JSON_PARSER.parse(json).getAsJsonObject().get("list"),
                new TypeToken<List<WxMpDataCubeUserCumulate>>() {
                }.getType());
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
