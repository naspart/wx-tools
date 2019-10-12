package com.naspat.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naspat.common.bean.menu.WxMenu;
import com.naspat.common.error.WxError;
import com.naspat.common.util.json.WxErrorAdapter;
import com.naspat.cp.bean.WxCpDepart;
import com.naspat.cp.bean.WxCpMessage;
import com.naspat.cp.bean.WxCpTag;
import com.naspat.cp.bean.WxCpUser;
import com.naspat.cp.util.json.adapter.*;

public class WxCpGsonBuilder {

    public static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxCpMessage.class, new WxCpMessageGsonAdapter());
        INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
        INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
        INSTANCE.registerTypeAdapter(WxMenu.class, new WxCpMenuGsonAdapter());
        INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }
}
