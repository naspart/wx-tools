package com.naspat.cp.bean.message_builder;

import com.naspat.common.api.WxConstant;
import com.naspat.cp.bean.WxCpMessage;
import com.naspat.cp.bean.article.MpnewsArticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * mpnews类型的图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.MPNEWS().addArticle(article).toUser(...).build();
 * </pre>
 */
public final class MpnewsBuilder extends BaseBuilder<MpnewsBuilder> {
    private List<MpnewsArticle> articles = new ArrayList<>();

    private String mediaId;

    public MpnewsBuilder() {
        this.msgType = WxConstant.KefuMsgType.MPNEWS;
    }

    public MpnewsBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public MpnewsBuilder addArticle(MpnewsArticle... articles) {
        Collections.addAll(this.articles, articles);
        return this;
    }

    public MpnewsBuilder articles(List<MpnewsArticle> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setMpnewsArticles(this.articles);
        if (this.mediaId != null) {
            m.setMediaId(this.mediaId);
        }

        return m;
    }
}
