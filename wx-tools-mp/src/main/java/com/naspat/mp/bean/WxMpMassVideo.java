package com.naspat.mp.bean;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 群发时用到的视频素材
 */
@Data
public class WxMpMassVideo implements Serializable {
    private static final long serialVersionUID = 6055523088377123404L;

    private String mediaId;
    private String title;
    private String description;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
