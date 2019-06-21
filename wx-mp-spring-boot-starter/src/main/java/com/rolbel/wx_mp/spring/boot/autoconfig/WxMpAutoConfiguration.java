package com.rolbel.wx_mp.spring.boot.autoconfig;

import com.rolbel.mp.api.WxMpService;
import com.rolbel.wx_mp.spring.boot.autoconfig.properties.WxMpProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
@ConditionalOnClass(WxMpService.class)
@Import({WxMpConfigAutoConfiguration.class, WxMpServiceAutoConfiguration.class})
public class WxMpAutoConfiguration {
}
