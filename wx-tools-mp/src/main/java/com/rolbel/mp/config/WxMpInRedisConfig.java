package com.rolbel.mp.config;

import com.rolbel.common.enums.TicketType;
import com.rolbel.mp.lock.RedisLock;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.concurrent.locks.Lock;

/**
 * 基于Redis的微信配置provider
 */
public class WxMpInRedisConfig extends WxMpInMemoryConfig {
    private static final String ACCESS_TOKEN_KEY = "wx_mp:access_token:";

    /**
     * 使用连接池保证线程安全
     */
    protected final Pool<Jedis> jedisPool;

    private String accessTokenKey;

    public WxMpInRedisConfig(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
        accessTokenLock = new RedisLock(jedisPool, "access_token");
        jsapiTicketLock = new RedisLock(jedisPool, "jsapi_ticket");
        sdkTicketLock = new RedisLock(jedisPool, "sdk_ticket");
        cardApiTicketLock = new RedisLock(jedisPool, "cardapi_ticket");
    }

    protected Lock accessTokenLock;
    protected Lock jsapiTicketLock;
    protected Lock sdkTicketLock;
    protected Lock cardApiTicketLock;

    /**
     * 每个公众号生成独有的存储key
     *
     * @param appId
     */
    @Override
    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
    }

    private String getTicketRedisKey(TicketType type) {
        return String.format("wx_mp:ticket:key:%s:%s", this.appId, type.getCode());
    }

    @Override
    public String getAccessToken() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.accessTokenKey);
        }
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.ttl(accessTokenKey) < 2;
        }
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.setex(this.accessTokenKey, expiresInSeconds - 200, accessToken);
        }
    }

    @Override
    public void expireAccessToken() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.expire(this.accessTokenKey, 0);
        }
    }

    @Override
    public String getTicket(TicketType type) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.getTicketRedisKey(type));
        }
    }

    @Override
    public Lock getTicketLock(TicketType type) {
        switch (type) {
            case SDK:
                return this.sdkTicketLock;
            case JSAPI:
                return this.jsapiTicketLock;
            case WX_CARD:
                return this.cardApiTicketLock;
            default:
                return null;
        }
    }

    @Override
    public boolean isTicketExpired(TicketType type) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.ttl(this.getTicketRedisKey(type)) < 2;
        }
    }

    @Override
    public synchronized void updateTicket(TicketType type, String jsapiTicket, int expiresInSeconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.setex(this.getTicketRedisKey(type), expiresInSeconds - 200, jsapiTicket);
        }
    }

    @Override
    public void expireTicket(TicketType type) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.expire(this.getTicketRedisKey(type), 0);
        }
    }
}
