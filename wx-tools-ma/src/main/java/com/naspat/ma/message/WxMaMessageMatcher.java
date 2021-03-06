package com.naspat.ma.message;

import com.naspat.ma.bean.WxMaMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxMaMessageMatcher {

  /**
   * 消息是否匹配某种模式
   */
  boolean match(WxMaMessage message);

}
