package com.rolbel.mp.bean.material;

import com.rolbel.common.util.ToStringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class WxMpMaterialFileBatchGetResult implements Serializable {
    private static final long serialVersionUID = 5060550760100826344L;

    private int totalCount;
    private int itemCount;
    private List<WxMaterialFileBatchGetNewsItem> items;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    @Data
    public static class WxMaterialFileBatchGetNewsItem {
        private String mediaId;
        private Date updateTime;
        private String name;
        private String url;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }
}
