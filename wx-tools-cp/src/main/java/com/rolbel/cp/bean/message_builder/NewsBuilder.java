package com.rolbel.cp.bean.message_builder;

import com.rolbel.common.api.WxConstant;
import com.rolbel.cp.bean.WxCpMessage;
import com.rolbel.cp.bean.article.NewArticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

    private List<NewArticle> articles = new ArrayList<>();

    public NewsBuilder() {
        this.msgType = WxConstant.KefuMsgType.NEWS;
    }

    public NewsBuilder addArticle(NewArticle... articles) {
        Collections.addAll(this.articles, articles);
        return this;
    }

    public NewsBuilder articles(List<NewArticle> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setArticles(this.articles);
        return m;
    }
}
