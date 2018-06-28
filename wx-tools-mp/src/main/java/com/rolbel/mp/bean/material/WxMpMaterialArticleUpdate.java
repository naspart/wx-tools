package com.rolbel.mp.bean.material;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpMaterialArticleUpdate implements Serializable {
    private static final long serialVersionUID = -3864915053361945078L;

    private String mediaId;
    private int index;
    private WxMpMaterialNews.WxMpMaterialNewsArticle articles;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
