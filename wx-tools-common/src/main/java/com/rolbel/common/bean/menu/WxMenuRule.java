package com.rolbel.common.bean.menu;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMenuRule implements Serializable {
    private static final long serialVersionUID = -5382180996257524071L;

    /**
     * 变态的微信接口，反序列化时这里反人类的使用和序列化时不一样的名字.
     */
    @SerializedName(value = "tag_id", alternate = "group_id")
    private String tagId;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String clientPlatformType;
    private String language;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
