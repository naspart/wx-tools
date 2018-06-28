package com.rolbel.cp.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rolbel.common.bean.menu.WxMenu;
import com.rolbel.common.util.json.WxMenuGsonAdapter;

import java.lang.reflect.Type;

/**
 * <pre>
 * 企业号菜单json转换适配器
 * </pre>
 */
public class WxCpMenuGsonAdapter extends WxMenuGsonAdapter {

    @Override
    public WxMenu deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return this.buildMenuFromJson(json.getAsJsonObject().get("button").getAsJsonArray());
    }
}
