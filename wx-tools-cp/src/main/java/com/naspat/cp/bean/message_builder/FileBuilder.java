package com.naspat.cp.bean.message_builder;

import com.naspat.common.api.WxConstant;
import com.naspat.cp.bean.WxCpMessage;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.FILE().mediaId(...).toUser(...).build();
 * </pre>
 */
public final class FileBuilder extends BaseBuilder<FileBuilder> {
    private String mediaId;

    public FileBuilder() {
        this.msgType = WxConstant.KefuMsgType.FILE;
    }

    public FileBuilder mediaId(String media_id) {
        this.mediaId = media_id;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
