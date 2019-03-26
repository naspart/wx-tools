package com.rolbel.ma.config;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class WxMaInRedisConfig extends WxMaInMemoryConfig {
    private static final String ACCESS_TOKEN_KEY = "wx_config:wx_ma:access_token:";
    private static final String JSAPI_TICKET_KEY = "wx_config:wx_ma:jsapi_ticket:";
    private static final String CARD_API_TICKET_KEY = "wx_config:wx_ma:card_api_ticket:";

    /**
     * 使用连接池保证线程安全
     */
    protected final Pool<Jedis> jedisPool;

    private String accessTokenKey;
    private String jsapiTicketKey;
    private String cardApiTicketKey;

    public WxMaInRedisConfig(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 每个公众号生成独有的存储key
     *
     * @param appId
     */
    @Override
    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
        this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
        this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appId);
        this.cardApiTicketKey = CARD_API_TICKET_KEY.concat(appId);
    }

    @Override
    public String getAccessToken() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.accessTokenKey);
        }
    }

    @Override
    public String getJsapiTicket() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.jsapiTicketKey);
        }
    }

    @Override
    public String getCardApiTicket() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(this.cardApiTicketKey);
        }
    }
}
