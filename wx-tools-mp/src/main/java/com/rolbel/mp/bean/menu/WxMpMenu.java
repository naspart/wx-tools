package com.rolbel.mp.bean.menu;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.bean.menu.WxMenuButton;
import com.rolbel.common.bean.menu.WxMenuRule;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.common.util.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *   公众号专用的菜单类，可能包含个性化菜单
 * Created by Binary Wang on 2017-1-17.
 * </pre>

 */
@Data
public class WxMpMenu implements Serializable {
    private static final long serialVersionUID = -5794350513426702252L;

    @SerializedName("menu")
    private WxMpConditionalMenu menu;

    @SerializedName("conditionalmenu")
    private List<WxMpConditionalMenu> conditionalMenu;

    public static WxMpMenu fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMpMenu.class);
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }

    @Data
    public static class WxMpConditionalMenu implements Serializable {
        private static final long serialVersionUID = -2279946921755382289L;

        @SerializedName("button")
        private List<WxMenuButton> buttons;
        @SerializedName("matchrule")
        private WxMenuRule rule;
        @SerializedName("menuid")
        private String menuId;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }
}
