package com.rolbel.wx_pay.spring.boot.autoconfig;

import com.rolbel.pay.api.WxPayService;
import com.rolbel.pay.api.impl.WxPayServiceImpl;
import com.rolbel.pay.config.WxPayConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxPayServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService(WxPayConfig wxPayConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setWxPayConfig(wxPayConfig);

        return wxPayService;
    }
}
