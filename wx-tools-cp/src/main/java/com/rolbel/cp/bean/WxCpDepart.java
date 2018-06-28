package com.rolbel.cp.bean;

import lombok.Data;
import com.rolbel.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 微信部门.
 */
@Data
public class WxCpDepart implements Serializable {
    private static final long serialVersionUID = 620569441670683913L;

    private Integer id;
    private String name;
    private Integer parentId;
    private Long order;

    public static WxCpDepart fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpDepart.class);
    }

    public String toJson() {
        return WxCpGsonBuilder.create().toJson(this);
    }
}
