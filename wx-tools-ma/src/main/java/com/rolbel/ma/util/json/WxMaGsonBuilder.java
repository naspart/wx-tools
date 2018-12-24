package com.rolbel.ma.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rolbel.ma.bean.WxMaTemplateMessage;
import com.rolbel.ma.bean.WxMaUniformMessage;
import com.rolbel.ma.bean.analysis.WxMaRetainInfo;
import com.rolbel.ma.bean.analysis.WxMaUserPortrait;
import com.rolbel.ma.bean.analysis.WxMaVisitDistribution;
import com.rolbel.ma.bean.code.WxMaCodeCommitRequest;
import com.rolbel.ma.bean.code.WxMaCodeVersionDistribution;

public class WxMaGsonBuilder {
    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxMaTemplateMessage.class, new WxMaTemplateMessageGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaUniformMessage.class, new WxMaUniformMessageGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaCodeCommitRequest.class, new WxMaCodeCommitRequestGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaCodeVersionDistribution.class, new WxMaCodeVersionDistributionGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaVisitDistribution.class, new WxMaVisitDistributionGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaRetainInfo.class, new WxMaRetainInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaUserPortrait.class, new WxMaUserPortraitGsonAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }
}
