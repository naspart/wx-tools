package com.naspat.ma.builder;

import com.naspat.ma.bean.WxMaKefuMessage;

import static com.naspat.ma.constant.WxMaConstant.KefuMsgType;

/**
 * 文本消息builder.
 */
public final class TextMessageBuilder extends BaseBuilder<TextMessageBuilder> {
    private String content;

    public TextMessageBuilder() {
        this.msgType = KefuMsgType.TEXT;
    }

    public TextMessageBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public WxMaKefuMessage build() {
        WxMaKefuMessage m = super.build();
        m.setText(new WxMaKefuMessage.KfText(this.content));
        return m;
    }
}
