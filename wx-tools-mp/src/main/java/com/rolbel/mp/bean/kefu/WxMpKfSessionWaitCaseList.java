package com.rolbel.mp.bean.kefu;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfSessionWaitCaseList implements Serializable {
    private static final long serialVersionUID = -3458119808028369340L;

    /**
     * count 未接入会话数量
     */
    @SerializedName("count")
    private Long count;

    /**
     * waitcaselist 未接入会话列表，最多返回100条数据
     */
    @SerializedName("waitcaselist")
    private List<WxMpKfSession> kfSessionWaitCaseList;

    public static WxMpKfSessionWaitCaseList fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json,
                WxMpKfSessionWaitCaseList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
