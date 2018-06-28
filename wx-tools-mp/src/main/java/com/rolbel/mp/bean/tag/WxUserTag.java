package com.rolbel.mp.bean.tag;

import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  用户标签对象
 * </pre>

 */
@Data
public class WxUserTag implements Serializable {
    private static final long serialVersionUID = 8181024173675141873L;

    /**
     * id	标签id，由微信分配
     */
    private Long id;

    /**
     * name	标签名，UTF8编码
     */
    private String name;

    /**
     * count 此标签下粉丝数
     */
    private Integer count;

    public static WxUserTag fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(json).getAsJsonObject().get("tag"),
                WxUserTag.class);
    }

    public static List<WxUserTag> listFromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(json).getAsJsonObject().get("tags"),
                new TypeToken<List<WxUserTag>>() {
                }.getType());
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
