package com.rolbel.ma.bean.code;

import com.rolbel.ma.util.json.WxMaGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信代码请求上传参数
 */
@Data
@Builder
public class WxMaCodeCommitRequest implements Serializable {
    private static final long serialVersionUID = 5209452369186358252L;

    /**
     * 代码库中的代码模版ID
     */
    private Long templateId;
    /**
     * 第三方自定义的配置
     */
    private WxMaCodeExtConfig extConfig;
    /**
     * 代码版本号，开发者可自定义
     */
    private String userVersion;
    /**
     * 代码描述，开发者可自定义
     */
    private String userDesc;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
