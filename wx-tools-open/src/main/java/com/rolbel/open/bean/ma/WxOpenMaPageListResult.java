package com.rolbel.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import com.rolbel.open.bean.WxOpenResult;
import com.rolbel.open.util.json.WxOpenGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 微信开放平台小程序第三方提交代码的页面配置列表.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaPageListResult extends WxOpenResult {
    private static final long serialVersionUID = -2557425979451875393L;

    @SerializedName("page_list")
    List<String> pageList;

    @Override
    public String toString() {
        return WxOpenGsonBuilder.create().toJson(this);
    }

}
