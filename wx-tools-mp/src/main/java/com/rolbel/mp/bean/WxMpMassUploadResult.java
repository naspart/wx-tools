package com.rolbel.mp.bean;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 上传群发用的素材的结果
 * 视频和图文消息需要在群发前上传素材
 * </pre>
 */
@Data
public class WxMpMassUploadResult implements Serializable {
    private static final long serialVersionUID = 6568157943644994029L;

    private String type;
    private String mediaId;
    private long createdAt;

    public static WxMpMassUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMassUploadResult.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
