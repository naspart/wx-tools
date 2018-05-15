package com.rolbel.mp.bean;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 群发时用到的视频素材
 *
 * @author chanjarster
 */
@Data
public class WxMpMassVideo implements Serializable {
    private static final long serialVersionUID = 9153925016061915637L;

    private String mediaId;
    private String title;
    private String description;

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }
}
