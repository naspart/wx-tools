package com.rolbel.cp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.rolbel.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxCpTag implements Serializable {
    private static final long serialVersionUID = -4322742608017066400L;

    private String id;

    private String name;


    public static WxCpTag fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpTag.class);
    }

    public String toJson() {
        return WxCpGsonBuilder.create().toJson(this);
    }
}
