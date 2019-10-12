package com.naspat.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naspat.common.bean.WxAccessToken;
import com.naspat.common.bean.menu.WxMenu;
import com.naspat.common.bean.result.WxMediaUploadResult;
import com.naspat.common.error.WxError;

public class WxGsonBuilder {
    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
        INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }
}
