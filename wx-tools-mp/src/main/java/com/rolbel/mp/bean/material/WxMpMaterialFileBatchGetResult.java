package com.rolbel.mp.bean.material;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WxMpMaterialFileBatchGetResult implements Serializable {
    private static final long serialVersionUID = 7407369548888554081L;

    private int totalCount;
    private int itemCount;
    private List<WxMaterialFileBatchGetNewsItem> items;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    @Data
    public static class WxMaterialFileBatchGetNewsItem {
        private String mediaId;
        private Date updateTime;
        private String name;
        private String url;

        public String toJson() {
            return WxMpGsonBuilder.create().toJson(this);
        }

        @Override
        public String toString() {
            return this.toJson();
        }
    }
}
