package com.naspat.mp.bean.shake_around;

import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpShakeAroundRelationSearchResult implements Serializable {
    private static final long serialVersionUID = -8773678516452446531L;

    private Integer errcode;
    private String errmsg;
    private WxMpShakeAcoundRelationSearch data;

    public static WxMpShakeAroundRelationSearchResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpShakeAroundRelationSearchResult.class);
    }

    @Data
    public static class WxMpShakeAcoundRelationSearch implements Serializable {
        private static final long serialVersionUID = 241993851109660318L;

        private List<WxMpShakeAroundDeviceIdentifier> relations;
        private Integer total_count;
    }
}
