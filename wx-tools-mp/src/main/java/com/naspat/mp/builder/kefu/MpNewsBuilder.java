package com.naspat.mp.builder.kefu;

import com.naspat.common.api.WxConstant;
import com.naspat.mp.bean.kefu.WxMpKefuMessage;

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
