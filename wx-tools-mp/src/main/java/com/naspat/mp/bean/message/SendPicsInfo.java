package com.naspat.mp.bean.message;

import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XStreamAlias("SendPicsInfo")
@Data
public class SendPicsInfo implements Serializable {
    private static final long serialVersionUID = -6272220093933504379L;

    @XStreamAlias("PicList")
    protected final List<Item> picList = new ArrayList<>();

    @XStreamAlias("Count")
    private Long count;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @XStreamAlias("item")
    @Data
    public static class Item implements Serializable {
        private static final long serialVersionUID = -6070121762285234035L;

        @XStreamAlias("PicMd5Sum")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String picMd5Sum;

        @Override
        public String toString() {
            return WxMpGsonBuilder.create().toJson(this);
        }
    }
}
