package com.rolbel.mp.bean.material;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialCountResult implements Serializable {
    private static final long serialVersionUID = 7298045066108162426L;

    private int voiceCount;
    private int videoCount;
    private int imageCount;
    private int newsCount;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}

