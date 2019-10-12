package com.naspat.ma.builder;

import com.naspat.ma.bean.WxMaKefuMessage;

import static com.naspat.ma.constant.WxMaConstant.KefuMsgType;

/**
 * 图片消息builder.
 */
public final class ImageMessageBuilder extends BaseBuilder<ImageMessageBuilder> {
    private String mediaId;

    public ImageMessageBuilder() {
        this.msgType = KefuMsgType.IMAGE;
    }

    public ImageMessageBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMaKefuMessage build() {
        WxMaKefuMessage m = super.build();
        m.setImage(new WxMaKefuMessage.KfImage(this.mediaId));
        return m;
    }
}
