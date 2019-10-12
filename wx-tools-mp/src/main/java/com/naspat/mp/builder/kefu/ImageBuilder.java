package com.naspat.mp.builder.kefu;

import com.naspat.common.api.WxConstant;
import com.naspat.mp.bean.kefu.WxMpKefuMessage;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.IMAGE().mediaId(...).toUser(...).build();
 * </pre>
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
    private String mediaId;

    public ImageBuilder() {
        this.msgType = WxConstant.KefuMsgType.IMAGE;
    }

    public ImageBuilder mediaId(String media_id) {
        this.mediaId = media_id;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
