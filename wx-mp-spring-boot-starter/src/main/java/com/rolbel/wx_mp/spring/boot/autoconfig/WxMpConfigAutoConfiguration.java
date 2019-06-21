package com.rolbel.wx_mp.spring.boot.autoconfig;

import com.rolbel.mp.config.WxMpConfig;
import com.rolbel.mp.config.WxMpInRedisConfig;
import com.rolbel.wx_mp.spring.boot.autoconfig.properties.WxMpProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Configuration
public class WxMpConfigAutoConfiguration {
    @Resource
    private WxMpProperties properties;
    @Resource
    private JedisPool jedisPool;

    @Bean
    @ConditionalOnBean(JedisPool.class)
    @ConditionalOnMissingBean(WxMpConfig.class)
    public WxMpConfig wxMpConfig() {
        WxMpInRedisConfig config = new WxMpInRedisConfig(jedisPool);
        config.setAppId(properties.getAppId());
        config.setSecret(properties.getSecret());
        config.setToken(properties.getToken());
        config.setAesKey(properties.getAesKey());

        return config;
    }
}
