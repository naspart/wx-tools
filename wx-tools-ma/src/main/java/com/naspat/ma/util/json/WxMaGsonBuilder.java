package com.naspat.ma.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naspat.ma.bean.WxMaTemplateMessage;
import com.naspat.ma.bean.WxMaUniformMessage;
import com.naspat.ma.bean.analysis.WxMaRetainInfo;
import com.naspat.ma.bean.analysis.WxMaUserPortrait;
import com.naspat.ma.bean.analysis.WxMaVisitDistribution;
import com.naspat.ma.bean.code.WxMaCodeCommitRequest;
import com.naspat.ma.bean.code.WxMaCodeVersionDistribution;

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
