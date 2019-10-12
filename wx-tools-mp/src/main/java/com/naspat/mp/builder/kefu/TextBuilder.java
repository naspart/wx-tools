package com.naspat.mp.builder.kefu;

import com.naspat.common.api.WxConstant;
import com.naspat.mp.bean.kefu.WxMpKefuMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
    private String content;

    public TextBuilder() {
        this.msgType = WxConstant.KefuMsgType.TEXT;
    }

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setContent(this.content);
        return m;
    }
}
