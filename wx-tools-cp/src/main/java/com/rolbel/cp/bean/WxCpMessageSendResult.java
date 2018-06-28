package com.rolbel.cp.bean;

import com.google.common.base.Splitter;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.cp.util.json.WxCpGsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 消息发送结果对象类.
 */
@Data
public class WxCpMessageSendResult implements Serializable {
    private static final long serialVersionUID = -4207822037514409797L;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public static WxCpMessageSendResult fromJson(String json) {
        return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpMessageSendResult.class);
    }

    @SerializedName("errcode")
    private Integer errCode;

    @SerializedName("errmsg")
    private String errMsg;

    @SerializedName("invaliduser")
    private String invalidUser;

    @SerializedName("invalidparty")
    private String invalidParty;

    @SerializedName("invalidtag")
    private String invalidTag;


    public List<String> getInvalidUserList() {
        return this.content2List(this.invalidUser);
    }

    private List<String> content2List(String content) {
        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }

        return Splitter.on("|").splitToList(content);
    }

    public List<String> getInvalidPartyList() {
        return this.content2List(this.invalidParty);
    }

    public List<String> getInvalidTagList() {
        return this.content2List(this.invalidTag);
    }
}
