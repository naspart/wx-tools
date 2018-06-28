package com.rolbel.cp.bean.article;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewArticle implements Serializable {
    private static final long serialVersionUID = 5875030108763559425L;

    private String title;
    private String description;
    private String url;
    private String picUrl;
}
