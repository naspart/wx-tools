package com.naspat.cp.bean;

import com.google.common.base.Splitter;
import com.google.gson.annotations.SerializedName;
import com.naspat.cp.util.json.WxCpGsonBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 邀请成员的结果对象类.
 */
@Data
public class WxCpInviteResult implements Serializable {

    @Override
    public String toString() {
        return WxCpGsonBuilder.create().toJson(this);
    }

    public static WxCpInviteResult fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpInviteResult.class);
    }

    @SerializedName("errcode")
    private Integer errCode;

    @SerializedName("errmsg")
    private String errMsg;

    @SerializedName("invaliduser")
    private String invalidUsers;

    @SerializedName("invalidparty")
    private String[] invalidParties;

    @SerializedName("invalidtag")
    private String[] invalidTags;

    public List<String> getInvalidUserList() {
        return this.content2List(this.invalidUsers);
    }

    private List<String> content2List(String content) {
        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }

        return Splitter.on("|").splitToList(content);
    }
}
