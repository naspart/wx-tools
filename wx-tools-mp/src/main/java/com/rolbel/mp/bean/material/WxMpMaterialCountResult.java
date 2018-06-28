package com.rolbel.mp.bean.material;

import com.rolbel.common.util.ToStringUtils;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialCountResult implements Serializable {
    private static final long serialVersionUID = 741669099194682684L;

    private int voiceCount;
    private int videoCount;
    private int imageCount;
    private int newsCount;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}

