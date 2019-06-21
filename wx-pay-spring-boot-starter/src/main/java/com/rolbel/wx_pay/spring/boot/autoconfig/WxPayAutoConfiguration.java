package com.rolbel.wx_pay.spring.boot.autoconfig;

import com.rolbel.pay.api.WxPayService;
import com.rolbel.wx_pay.spring.boot.autoconfig.properties.WxPayProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
@ConditionalOnClass(WxPayService.class)
@Import({WxPayConfigAutoConfiguration.class, WxPayServiceAutoConfiguration.class})
public class WxPayAutoConfiguration {
}
