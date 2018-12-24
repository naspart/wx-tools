package com.rolbel.mp.bean.material;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WxMpMaterialNewsBatchGetResult implements Serializable {
    private static final long serialVersionUID = -5892455617384597691L;

    private int totalCount;
    private int itemCount;
    private List<WxMaterialNewsBatchGetNewsItem> items;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Data
    public static class WxMaterialNewsBatchGetNewsItem {
        private String mediaId;
        private Date updateTime;
        private WxMpMaterialNews content;

        @Override
        public String toString() {
            return WxMpGsonBuilder.create().toJson(this);
        }
    }
}
