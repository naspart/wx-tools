package com.naspat.mp.bean;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 语义理解查询结果对象
 * <p>
 * http://mp.weixin.qq.com/wiki/index.php?title=语义理解
 */
@Data
public class WxMpSemanticQueryResult implements Serializable {
    private static final long serialVersionUID = 8767191806089105446L;

    private String query;
    private String type;
    private String semantic;
    private String result;
    private String answer;
    private String text;

    public static WxMpSemanticQueryResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpSemanticQueryResult.class);
    }
}
