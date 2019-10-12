package com.naspat.mp.bean.user;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class WxMpUserBlacklistGetResult implements Serializable {
    private static final long serialVersionUID = 1900461876081603186L;

    protected int total = -1;
    protected int count = -1;
    protected List<String> openidList = new ArrayList<>();
    protected String nextOpenid;

    public static WxMpUserBlacklistGetResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpUserBlacklistGetResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
