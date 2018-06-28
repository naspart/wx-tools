package com.rolbel.cp.bean.article;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(builderMethodName = "newBuilder")
public class MpnewsArticle implements Serializable {
    private static final long serialVersionUID = 1145883212522703764L;

    private String title;
    private String thumbMediaId;
    private String author;
    private String contentSourceUrl;
    private String content;
    private String digest;
    private String showCoverPic;
}
