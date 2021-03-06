package com.naspat.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import com.naspat.open.bean.WxOpenResult;
import com.naspat.open.util.json.WxOpenGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 微信开放平台小程序体验者列表返回.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxOpenMaTesterListResult extends WxOpenResult {
    private static final long serialVersionUID = -613936397557067111L;

    @SerializedName("members")
    List<WxOpenMaMember> membersList;

    @Override
    public String toString() {
        return WxOpenGsonBuilder.create().toJson(this);
    }
}
