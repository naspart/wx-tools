package com.rolbel.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpKfSessionList implements Serializable {
    private static final long serialVersionUID = -7680371346226640206L;

    /**
     * 会话列表
     */
    @SerializedName("sessionlist")
    private List<WxMpKfSession> kfSessionList;

    public static WxMpKfSessionList fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json,
                WxMpKfSessionList.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
