package com.naspat.open.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naspat.open.bean.WxOpenAuthorizerAccessToken;
import com.naspat.open.bean.WxOpenComponentAccessToken;
import com.naspat.open.bean.auth.WxOpenAuthorizationInfo;
import com.naspat.open.bean.auth.WxOpenAuthorizerInfo;
import com.naspat.open.bean.result.WxOpenAuthorizerInfoResult;
import com.naspat.open.bean.result.WxOpenAuthorizerOptionResult;
import com.naspat.open.bean.result.WxOpenQueryAuthResult;

public class WxOpenGsonBuilder {

    public static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxOpenComponentAccessToken.class, new WxOpenComponentAccessTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerAccessToken.class, new WxOpenAuthorizerAccessTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizationInfo.class, new WxOpenAuthorizationInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerInfo.class, new WxOpenAuthorizerInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenQueryAuthResult.class, new WxOpenQueryAuthResultGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerInfoResult.class, new WxOpenAuthorizerInfoResultGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerOptionResult.class, new WxOpenAuthorizerOptionResultGsonAdapter());

    }

    public static Gson create() {
        return INSTANCE.create();
    }
}
