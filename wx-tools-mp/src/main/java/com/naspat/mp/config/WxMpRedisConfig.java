package com.naspat.mp.config;

import com.naspat.common.enums.TicketType;
import com.naspat.mp.bean.WxMpHostConfig;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

import java.io.File;

/**
 * 基于Redis的微信配置provider
 */
@Getter
public class WxMpRedisConfig implements WxMpConfig {

    protected volatile String appId;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String templateId;
    protected volatile String aesKey;

    protected volatile String httpProxyHost;
    protected volatile int httpProxyPort;
    protected volatile String httpProxyUsername;
    protected volatile String httpProxyPassword;

    /**
     * 使用连接池保证线程安全
     */
    protected final Pool<Jedis> jedisPool;

    public static WxMpRedisConfig.Builder builder() {
        return new WxMpRedisConfig.Builder();
    }

    public static class Builder {
        private String appId;
        private String secret;
        private String token;
        private String templateId;
        private String aesKey;

        private String httpProxyHost;
        private int httpProxyPort;
        private String httpProxyUsername;
        private String httpProxyPassword;

        private Pool<Jedis> jedisPool;

        public WxMpRedisConfig.Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public WxMpRedisConfig.Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public WxMpRedisConfig.Builder token(String token) {
            this.token = token;
            return this;
        }

        public WxMpRedisConfig.Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public WxMpRedisConfig.Builder aesKey(String aesKey) {
            this.aesKey = aesKey;
            return this;
        }

        public WxMpRedisConfig.Builder jedisPool(Pool<Jedis> jedisPool) {
            this.jedisPool = jedisPool;
            return this;
        }


        public WxMpRedisConfig.Builder httpProxyHost(String httpProxyHost) {
            this.httpProxyHost = httpProxyHost;
            return this;
        }

        public WxMpRedisConfig.Builder httpProxyPort(int httpProxyPort) {
            this.httpProxyPort = httpProxyPort;
            return this;
        }

        public WxMpRedisConfig.Builder httpProxyUsername(String httpProxyUsername) {
            this.httpProxyUsername = httpProxyUsername;
            return this;
        }

        public WxMpRedisConfig.Builder httpProxyPassword(String httpProxyPassword) {
            this.httpProxyPassword = httpProxyPassword;
            return this;
        }

        public WxMpRedisConfig build() {
            return new WxMpRedisConfig(this);
        }
    }

    private WxMpRedisConfig(WxMpRedisConfig.Builder builder) {
        appId = builder.appId;
        secret = builder.secret;
        token = builder.token;
        templateId = builder.templateId;
        aesKey = builder.aesKey;

        httpProxyHost = builder.httpProxyHost;
        httpProxyPort = builder.httpProxyPort;
        httpProxyUsername = builder.httpProxyUsername;
        httpProxyPassword = builder.httpProxyPassword;

        jedisPool = builder.jedisPool;
    }

    private String getTokenKey() {
        return String.format("wx_config:wx_mp:access_token:%s", this.appId);
    }

    private String getTicketKey(TicketType type) {
        return String.format("wx_config:wx_mp:ticket_key:%s:%s", this.appId, type.getCode());
    }

    @Override
    public String getAccessToken() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.getTokenKey());
        }
    }

    @Override
    public String getTicket(TicketType type) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.getTicketKey(type));
        }
    }

    @Override
    public File getTmpDirFile() {
        return null;
    }

    @Override
    public WxMpHostConfig getHostConfig() {
        return null;
    }
}
