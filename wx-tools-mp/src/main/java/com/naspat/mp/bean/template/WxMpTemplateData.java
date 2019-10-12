package com.naspat.mp.bean.template;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WxMpTemplateData implements Serializable {
    private static final long serialVersionUID = 5327936714226944612L;

    private String name;
    private String value;
    private String color;

    public WxMpTemplateData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public WxMpTemplateData(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
