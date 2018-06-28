package com.rolbel.mp.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.rolbel.common.util.ToStringUtils;
import com.rolbel.common.util.json.WxBooleanTypeAdapter;
import com.rolbel.common.util.json.WxDateTypeAdapter;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 公众号的自动回复规则
 * </pre>
 */
@Data
public class WxMpCurrentAutoReplyInfo implements Serializable {
    private static final long serialVersionUID = -6202671535688747490L;

    @SerializedName("is_add_friend_reply_open")
    @JsonAdapter(WxBooleanTypeAdapter.class)
    private Boolean isAddFriendReplyOpen;

    @SerializedName("is_autoreply_open")
    @JsonAdapter(WxBooleanTypeAdapter.class)
    private Boolean isAutoReplyOpen;

    @SerializedName("add_friend_autoreply_info")
    private AutoReplyInfo addFriendAutoReplyInfo;

    @SerializedName("message_default_autoreply_info")
    private AutoReplyInfo messageDefaultAutoReplyInfo;

    @SerializedName("keyword_autoreply_info")
    private KeywordAutoReplyInfo keywordAutoReplyInfo;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public static WxMpCurrentAutoReplyInfo fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpCurrentAutoReplyInfo.class);
    }

    @Data
    public static class AutoReplyRule implements Serializable {
        private static final long serialVersionUID = 8747435146449288637L;

        @SerializedName("rule_name")
        private String ruleName;

        @SerializedName("create_time")
        @JsonAdapter(WxDateTypeAdapter.class)
        private Date createTime;

        @SerializedName("reply_mode")
        private String replyMode;

        @SerializedName("keyword_list_info")
        private List<KeywordInfo> keywordListInfo;

        @SerializedName("reply_list_info")
        private List<ReplyInfo> replyListInfo;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }

    @Data
    public static class ReplyInfo implements Serializable {
        private static final long serialVersionUID = 2177446368400776864L;

        private String type;
        private String content;

        @SerializedName("news_info")
        private NewsInfo newsInfo;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }

    @Data
    public static class NewsInfo implements Serializable {
        private static final long serialVersionUID = 7741271515574197646L;

        private List<NewsItem> list;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }

    @Data
    public static class NewsItem implements Serializable {
        private static final long serialVersionUID = 987806136389161878L;

        @SerializedName("cover_url")
        private String coverUrl;

        private String author;

        @SerializedName("content_url")
        private String contentUrl;

        private String digest;

        @SerializedName("show_cover")
        @JsonAdapter(WxBooleanTypeAdapter.class)
        private Boolean showCover;

        @SerializedName("source_url")
        private String sourceUrl;

        private String title;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }

    @Data
    public static class KeywordInfo implements Serializable {
        private static final long serialVersionUID = -8728664611520449243L;

        private String type;

        @SerializedName("match_mode")
        private String matchMode;

        private String content;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }

    @Data
    public static class KeywordAutoReplyInfo implements Serializable {
        private static final long serialVersionUID = -1987647674609291563L;

        private List<AutoReplyRule> list;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }

    @Data
    public static class AutoReplyInfo implements Serializable {
        private static final long serialVersionUID = -4096024581468389164L;

        private String type;
        private String content;

        @Override
        public String toString() {
            return ToStringUtils.toSimpleString(this);
        }
    }
}
