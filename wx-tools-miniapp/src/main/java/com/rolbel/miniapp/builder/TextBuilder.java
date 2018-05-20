package com.rolbel.miniapp.builder;

import com.rolbel.miniapp.bean.WxMaKefuMessage;
import com.rolbel.miniapp.constant.WxMaConstant;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
  private String content;

  public TextBuilder() {
    this.msgType = WxMaConstant.KefuMsgType.TEXT;
  }

  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setContent(this.content);
    return m;
  }
}
