package com.naspat.mp.bean.shake_around;

import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpShakeAroundPageAddQuery implements Serializable {
    private static final long serialVersionUID = 9072256246441239325L;

    private String title;
    private String description;
    private String pageUrl;
    private String comment;
    private String iconUrl;

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("description", description);
        jsonObject.addProperty("page_url", pageUrl);
        jsonObject.addProperty("comment", comment);
        jsonObject.addProperty("icon_url", iconUrl);

        return jsonObject.toString();
    }
}
