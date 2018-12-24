package com.rolbel.mp.bean.card;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberCardActivateUserFormResult implements Serializable {
    private Integer errcode;
    private String errmsg;

    public boolean isSuccess() {
        return 0 == errcode;
    }

    public static MemberCardActivateUserFormResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, MemberCardActivateUserFormResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}

