package com.naspat.mp.bean.kefu;

import com.naspat.mp.builder.kefu.*;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客服消息
 */
@Data
public class WxMpKefuMessage implements Serializable {
    private static final long serialVersionUID = -6803197351739316092L;

    private String toUser;
    private String msgType;
    private String content;
    private String mediaId;
    private String thumbMediaId;
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;
    private String kfAccount;
    private String cardId;
    private String mpNewsMediaId;
    private String miniProgramAppId;
    private String miniProgramPagePath;
    private List<WxArticle> articles = new ArrayList<>();

    /**
     * 获得文本消息builder
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得音乐消息builder
     */
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    /**
     * 获得图文消息（点击跳转到外链）builder
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    /**
     * 获得图文消息（点击跳转到图文消息页面）builder
     */
    public static MpNewsBuilder MPNEWS() {
        return new MpNewsBuilder();
    }

    /**
     * 获得卡券消息builder
     */
    public static WxCardBuilder WXCARD() {
        return new WxCardBuilder();
    }

    /**
     * 小程序卡片
     */
    public static MiniProgramPageBuilder MINIPROGRAMPAGE() {
        return new MiniProgramPageBuilder();
    }

    /**
     * <pre>
     * 请使用
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#TEXT}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#IMAGE}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#VOICE}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#MUSIC}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#VIDEO}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#NEWS}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#MPNEWS}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#WXCARD}
     * {@link com.naspat.common.api.WxConstant.KefuMsgType#MINIPROGRAMPAGE}
     * </pre>
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Data
    public static class WxArticle implements Serializable {
        private static final long serialVersionUID = -5204110964179469159L;

        private String title;
        private String description;
        private String url;
        private String picUrl;
    }
}
