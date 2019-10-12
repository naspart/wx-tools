package com.naspat.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamCDataConverter;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@XStreamAlias("xml")
@Data
public class WxCpXmlOutNewsMessage extends WxCpXmlOutMessage {
    private static final long serialVersionUID = 2244938696322486072L;

    @XStreamAlias("Articles")
    protected final List<Item> articles = new ArrayList<>();

    @XStreamAlias("ArticleCount")
    protected int articleCount;

    public WxCpXmlOutNewsMessage() {
        this.msgType = WxConstant.XmlMsgType.NEWS;
    }


    public void addArticle(Item item) {
        this.articles.add(item);
        this.articleCount = this.articles.size();
    }

    @XStreamAlias("item")
    @Data
    public static class Item implements Serializable {
        private static final long serialVersionUID = -437318416899895693L;

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
