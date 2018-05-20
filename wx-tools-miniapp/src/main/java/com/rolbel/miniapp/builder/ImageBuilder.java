package com.rolbel.miniapp.builder;

import com.rolbel.miniapp.bean.WxMaKefuMessage;
import com.rolbel.miniapp.constant.WxMaConstant;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
    private String mediaId;

    public ImageBuilder() {
        this.msgType = WxMaConstant.KefuMsgType.IMAGE;
    }

    public ImageBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMaKefuMessage build() {
        WxMaKefuMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
