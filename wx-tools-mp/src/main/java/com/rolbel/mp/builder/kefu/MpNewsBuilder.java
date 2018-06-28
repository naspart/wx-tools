package com.rolbel.mp.builder.kefu;

import com.rolbel.common.api.WxConstant;
import com.rolbel.mp.bean.kefu.WxMpKefuMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.NEWS().mediaId("xxxxx").toUser(...).build();
 * </pre>
 */
public final class MpNewsBuilder extends BaseBuilder<MpNewsBuilder> {
    private String mediaId;

    public MpNewsBuilder() {
        this.msgType = WxConstant.KefuMsgType.MPNEWS;
    }

    public MpNewsBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setMpNewsMediaId(this.mediaId);
        return m;
    }
}
