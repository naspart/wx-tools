package com.rolbel.mp.bean.user;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 关注者列表
 */
@Data
public class WxMpUserList implements Serializable {
    private static final long serialVersionUID = 2784581454964077700L;

    protected long total = -1;
    protected int count = -1;
    protected List<String> openids = new ArrayList<>();
    protected String nextOpenid;

    public static WxMpUserList fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpUserList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
