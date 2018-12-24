package com.rolbel.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rolbel.common.bean.menu.WxMenu;
import com.rolbel.common.error.WxError;
import com.rolbel.common.util.json.WxErrorAdapter;
import com.rolbel.cp.bean.WxCpDepart;
import com.rolbel.cp.bean.WxCpMessage;
import com.rolbel.cp.bean.WxCpTag;
import com.rolbel.cp.bean.WxCpUser;
import com.rolbel.cp.util.json.adapter.*;

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
