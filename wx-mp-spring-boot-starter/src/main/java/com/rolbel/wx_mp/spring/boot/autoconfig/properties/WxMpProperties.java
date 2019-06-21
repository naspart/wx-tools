package com.rolbel.wx_mp.spring.boot.autoconfig.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.rolbel.wx_mp.spring.boot.autoconfig.properties.WxMpProperties.PREFIX;

@Data
@ConfigurationProperties(PREFIX)
public class WxMpProperties {
    public static final String PREFIX = "wx.mp";

    private String appId;

    private String secret;

    private String token;

    private String aesKey;
}
