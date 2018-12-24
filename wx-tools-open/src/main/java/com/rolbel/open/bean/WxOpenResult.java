package com.rolbel.open.bean;

import com.rolbel.open.util.json.WxOpenGsonBuilder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 基础的微信开放平台请求结果.
 */
@Data
public class WxOpenResult implements Serializable {
    private static final long serialVersionUID = -3806463487128789477L;

    protected String errcode;
    protected String errmsg;

    /**
     * 请求是否成功.
     */
    public boolean isSuccess() {
        return StringUtils.equalsIgnoreCase(errcode, "0");
    }

    @Override
    public String toString() {
        return WxOpenGsonBuilder.create().toJson(this);
    }
}
