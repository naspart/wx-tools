package com.naspat.mp.bean.message;

import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@XStreamAlias("xml")
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutNewsMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -4960068054849530487L;

    @XStreamAlias("Articles")
    protected final List<Item> articles = new ArrayList<>();

    @XStreamAlias("ArticleCount")
    protected int articleCount;

    public WxMpXmlOutNewsMessage() {
        this.msgType = WxConstant.XmlMsgType.NEWS;
    }

    public void addArticle(Item item) {
        this.articles.add(item);
        this.articleCount = this.articles.size();
    }

    @Data
    @XStreamAlias("item")
    public static class Item implements Serializable {
        private static final long serialVersionUID = 5117627702623713113L;

        @XStreamAlias("Title")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String title;

        @XStreamAlias("Description")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String description;

        @XStreamAlias("PicUrl")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String picUrl;

        @XStreamAlias("Url")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String url;
    }
}
