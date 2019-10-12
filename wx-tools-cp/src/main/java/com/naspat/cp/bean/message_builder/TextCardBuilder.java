package com.naspat.cp.bean.message_builder;

import com.naspat.common.api.WxConstant;
import com.naspat.cp.bean.WxCpMessage;

/**
 * <pre>
 * 文本卡片消息Builder
 * 用法: WxCustomMessage m = WxCustomMessage.TEXTCARD().title(...)....toUser(...).build();
 * </pre>
 */
public class TextCardBuilder extends BaseBuilder<TextCardBuilder> {
    private String title;
    private String description;
    private String url;

    public TextCardBuilder() {
        this.msgType = WxConstant.KefuMsgType.TEXTCARD;
    }

    public TextCardBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TextCardBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TextCardBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setTitle(this.title);
        m.setDescription(this.description);
        m.setUrl(this.url);
        return m;
    }
}
