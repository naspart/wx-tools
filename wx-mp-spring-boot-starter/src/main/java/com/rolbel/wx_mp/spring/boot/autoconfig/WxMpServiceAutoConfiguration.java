package com.rolbel.wx_mp.spring.boot.autoconfig;

import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.impl.WxMpServiceImpl;
import com.rolbel.mp.config.WxMpConfig;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class WxMpServiceAutoConfiguration {
    @Resource
    private ApplicationContext ctx;
    @Resource
    private WxMpConfig wxMpConfig;

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfig(wxMpConfig);
        registerWxMpSubService(wxMpService);

        return wxMpService;
    }

    @ConditionalOnBean(WxMpService.class)
    public Object registerWxMpSubService(WxMpService wxMpService) {
        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        factory.registerSingleton("wxMpKefuService", wxMpService.getKefuService());
        factory.registerSingleton("wxMpMaterialService", wxMpService.getMaterialService());
        factory.registerSingleton("wxMpMenuService", wxMpService.getMenuService());
        factory.registerSingleton("wxMpUserService", wxMpService.getUserService());
        factory.registerSingleton("wxMpUserTagService", wxMpService.getUserTagService());
        factory.registerSingleton("wxMpQrcodeService", wxMpService.getQrcodeService());
        factory.registerSingleton("wxMpCardService", wxMpService.getCardService());
        factory.registerSingleton("wxMpDataCubeService", wxMpService.getDataCubeService());
        factory.registerSingleton("wxMpUserBlacklistService", wxMpService.getBlackListService());
        factory.registerSingleton("wxMpStoreService", wxMpService.getStoreService());
        factory.registerSingleton("wxMpTemplateMsgService", wxMpService.getTemplateMsgService());
        factory.registerSingleton("wxMpSubscribeMsgService", wxMpService.getSubscribeMsgService());
        factory.registerSingleton("wxMpDeviceService", wxMpService.getDeviceService());
        factory.registerSingleton("wxMpShakeService", wxMpService.getShakeService());
        factory.registerSingleton("wxMpMemberCardService", wxMpService.getMemberCardService());
        factory.registerSingleton("wxMpMassMessageService", wxMpService.getMassMessageService());
        return Boolean.TRUE;
    }

}
