package com.rolbel.miniapp.builder;

import com.rolbel.miniapp.bean.WxMaKefuMessage;

import static com.rolbel.miniapp.constant.WxMaConstant.KefuMsgType;

/**
 * 图片消息builder.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
