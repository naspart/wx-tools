package com.rolbel.wx_pay.spring.boot.autoconfig;

import com.rolbel.pay.config.WxPayConfig;
import com.rolbel.wx_pay.spring.boot.autoconfig.properties.WxPayProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class WxPayConfigAutoConfiguration {
    @Resource
    private WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean(WxPayConfig.class)
    public WxPayConfig wxPayConfig() {
        WxPayConfig config = new WxPayConfig();
        config.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
        config.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
        config.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
        config.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        config.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
        config.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));

        return config;
    }
}
