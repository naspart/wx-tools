package com.rolbel.cp.bean;

import com.google.common.base.Splitter;
import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtil;
import com.rolbel.cp.util.json.WxCpGsonBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 邀请成员的结果对象类.
 * Created by Binary Wang on 2018-5-13.

 */
@Data
public class WxCpInviteResult implements Serializable {
    private static final long serialVersionUID = 1420065684270213578L;

    @Override
    public String toString() {
        return ToStringUtil.toSimpleString(this);
    }

    public static WxCpInviteResult fromJson(String json) {
        return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpInviteResult.class);
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
