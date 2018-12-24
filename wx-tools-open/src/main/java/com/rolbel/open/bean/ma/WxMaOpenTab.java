package com.rolbel.open.bean.ma;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class WxMaOpenTab implements Serializable {
    @NonNull
    private String pagePath;

    @NonNull
    private String text;
    private String iconPath;
    private String selectedIconPath;
}
