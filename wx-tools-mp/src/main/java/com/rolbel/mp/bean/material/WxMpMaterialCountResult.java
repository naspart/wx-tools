package com.rolbel.mp.bean.material;

import com.rolbel.common.util.ToStringUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialCountResult implements Serializable {
    private static final long serialVersionUID = -5568772662085874138L;

    private int voiceCount;
    private int videoCount;
    private int imageCount;
    private int newsCount;

    @Override
    public String toString() {
        return ToStringUtil.toSimpleString(this);
    }
}

