package com.naspat.ma.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WxMaTemplateData {
    private String name;
    private String value;
    private String color;

    public WxMaTemplateData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public WxMaTemplateData(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
