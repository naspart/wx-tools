package com.rolbel.mp.bean.material;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialUploadResult implements Serializable {
    private static final long serialVersionUID = 5707419878819595025L;

    private String mediaId;
    private String url;
    private Integer errCode;
    private String errMsg;

    public static WxMpMaterialUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialUploadResult.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

}

