package com.rolbel.cp.bean.message_builder;

import com.rolbel.common.api.WxConstant;
import com.rolbel.cp.bean.WxCpMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.TEXT().content(...).toUser(...).build();
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
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setContent(this.content);
        return m;
    }
}
