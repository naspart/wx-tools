package com.rolbel.miniapp.bean;

import com.rolbel.miniapp.builder.ImageBuilder;
import com.rolbel.miniapp.builder.TextBuilder;
import com.rolbel.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 客服消息
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class WxMaKefuMessage implements Serializable {
    private static final long serialVersionUID = -9196732086954365246L;

    private String toUser;
    private String msgType;
    private String content;
    private String mediaId;
    private String thumbMediaId;
    private String title;
    private String description;

    /**
     * 获得文本消息builder
     */
    public static TextBuilder newTextBuilder() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder
     */
    public static ImageBuilder newImageBuilder() {
        return new ImageBuilder();
    }

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }

}
