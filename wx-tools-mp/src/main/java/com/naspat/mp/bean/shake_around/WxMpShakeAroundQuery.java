package com.naspat.mp.bean.shake_around;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class WxMpShakeAroundQuery implements Serializable {
    private static final long serialVersionUID = 846937413800835466L;

    private String ticket;

    private int needPoi;

    public String toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("ticket", this.ticket);
        map.put("need_poi", this.needPoi);

        return new Gson().toJson(map);
    }
}
