package com.rolbel.miniapp.util.json;

import com.rolbel.miniapp.bean.WxMaKefuMessage;
import com.rolbel.miniapp.bean.WxMaTemplateMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMaGsonBuilder {
  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMaKefuMessage.class, new WxMaKefuMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMaTemplateMessage.class, new WxMaTemplateMessageGsonAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
