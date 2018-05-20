package com.rolbel.miniapp.message;

import com.rolbel.miniapp.bean.WxMaMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxMaMessageMatcher {

    /**
     * 消息是否匹配某种模式
     */
    boolean match(WxMaMessage message);

}
