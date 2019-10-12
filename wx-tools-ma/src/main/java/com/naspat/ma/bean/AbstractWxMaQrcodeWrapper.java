package com.naspat.ma.bean;

import com.naspat.ma.util.json.WxMaGsonBuilder;

/**
 * 微信二维码（小程序码）包装器.
 */
public abstract class AbstractWxMaQrcodeWrapper {
  public String toJson() {
    return WxMaGsonBuilder.create().toJson(this);
  }

  @Override
  public String toString() {
    return this.toJson();
  }
}
