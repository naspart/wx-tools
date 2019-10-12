package com.naspat.mp.builder.kefu;


import com.naspat.common.api.WxConstant;
import com.naspat.mp.bean.kefu.WxMpKefuMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {
    private List<WxMpKefuMessage.WxArticle> articles = new ArrayList<>();

    public NewsBuilder() {
        this.msgType = WxConstant.KefuMsgType.NEWS;
    }

    public NewsBuilder addArticle(WxMpKefuMessage.WxArticle... articles) {
        Collections.addAll(this.articles, articles);
        return this;
    }

    public NewsBuilder articles(List<WxMpKefuMessage.WxArticle> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setArticles(this.articles);
        return m;
    }
}
