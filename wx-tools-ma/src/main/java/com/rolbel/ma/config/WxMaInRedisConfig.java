package com.rolbel.ma.config;

import com.github.jedis.lock.JedisLock;
import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WxMaInRedisConfig implements WxMaConfig {
    private static final String ACCESS_TOKEN_KEY = "wx_config:wx_ma:access_token:";
    private static final String JSAPI_TICKET_KEY = "wx_config:wx_ma:jsapi_ticket:";
    private static final String CARD_API_TICKET_KEY = "wx_config:wx_ma:card_api_ticket:";

    private static final String ACCESS_TOKEN_LOCK_KEY = "wx_config:wx_ma:access_token_lock:";
    private static final String JSAPI_TICKET_LOCK_KEY = "wx_config:wx_ma:jsapi_ticket_lock:";
    private static final String CARD_API_TICKET_LOCK_KEY = "wx_config:wx_ma:card_api_ticket_lock:";

    private static final String HASH_VALUE_FIELD = "value";
    private static final String HASH_EXPIRE_FIELD = "expire";

    /**
     * 使用连接池保证线程安全
     */
    protected final Pool<Jedis> jedisPool;

    private volatile String msgDataFormat;
    protected volatile String appId;
    private volatile String secret;
    protected volatile String token;
    private volatile String aesKey;

    private volatile String httpProxyHost;
    private volatile int httpProxyPort;
    private volatile String httpProxyUsername;
    private volatile String httpProxyPassword;

    private Lock accessTokenLock;
    private Lock jsapiTicketLock;
    private Lock cardApiTicketLock;

    protected volatile File tmpDirFile;

    private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    public WxMaInRedisConfig(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    private String getValueFromRedis(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(key, HASH_VALUE_FIELD);
        }
    }

    private void setValueToRedis(String key, long expiresTime, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> hash = new HashMap<>();
            hash.put(HASH_VALUE_FIELD, value);
            hash.put(HASH_EXPIRE_FIELD, String.valueOf(expiresTime));
            jedis.hmset(key, hash);
        }
    }

    private long getExpireFromRedis(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String expire = jedis.hget(key, HASH_EXPIRE_FIELD);
            return expire == null ? 0 : Long.parseLong(expire);
        }
    }

    private void setExpire(String key, long expiresTime) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, HASH_EXPIRE_FIELD, String.valueOf(expiresTime));
        }
    }

    @Override
    public String getAccessToken() {
        return getValueFromRedis(ACCESS_TOKEN_KEY);
    }

    @Override
    public Lock getAccessTokenLock() {
        if (accessTokenLock == null) {
            synchronized (this) {
                if (accessTokenLock == null) {
                    accessTokenLock = new DistributedLock(ACCESS_TOKEN_LOCK_KEY);
                }
            }
        }
        return accessTokenLock;
    }


    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > getExpireFromRedis(ACCESS_TOKEN_KEY);
    }

    @Override
    public void expireAccessToken() {
        setExpire(ACCESS_TOKEN_KEY, 0);
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        setValueToRedis(ACCESS_TOKEN_KEY, System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L, accessToken);
    }

    @Override
    public String getJsapiTicket() {
        return getValueFromRedis(JSAPI_TICKET_KEY);
    }

    @Override
    public Lock getJsapiTicketLock() {
        if (jsapiTicketLock == null) {
            synchronized (this) {
                if (jsapiTicketLock == null) {
                    jsapiTicketLock = new DistributedLock(JSAPI_TICKET_LOCK_KEY);
                }
            }
        }
        return jsapiTicketLock;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return System.currentTimeMillis() > getExpireFromRedis(JSAPI_TICKET_KEY);
    }

    @Override
    public void expireJsapiTicket() {
        setExpire(JSAPI_TICKET_KEY, 0);
    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        setValueToRedis(JSAPI_TICKET_KEY, System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L, jsapiTicket);
    }

    @Override
    public String getCardApiTicket() {
        return getValueFromRedis(CARD_API_TICKET_KEY);
    }

    @Override
    public Lock getCardApiTicketLock() {
        if (cardApiTicketLock == null) {
            synchronized (this) {
                if (cardApiTicketLock == null) {
                    cardApiTicketLock = new DistributedLock(CARD_API_TICKET_LOCK_KEY);
                }
            }
        }
        return cardApiTicketLock;
    }

    @Override
    public boolean isCardApiTicketExpired() {
        return System.currentTimeMillis() > getExpireFromRedis(CARD_API_TICKET_KEY);
    }

    @Override
    public void expireCardApiTicket() {
        setExpire(CARD_API_TICKET_KEY, 0);
    }

    @Override
    public void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        setValueToRedis(CARD_API_TICKET_KEY, System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L, cardApiTicket);
    }

    @Override
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public String getMsgDataFormat() {
        return this.msgDataFormat;
    }

    public void setMsgDataFormat(String msgDataFormat) {
        this.msgDataFormat = msgDataFormat;
    }

    @Override
    public String getHttpProxyHost() {
        return this.httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override
    public int getHttpProxyPort() {
        return this.httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override
    public String getHttpProxyUsername() {
        return this.httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    @Override
    public String getHttpProxyPassword() {
        return this.httpProxyPassword;
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return this.apacheHttpClientBuilder;
    }

    public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    /**
     * 基于redis的简单分布式锁.
     */
    private class DistributedLock implements Lock {

        private JedisLock lock;

        private DistributedLock(String key) {
            this.lock = new JedisLock(key);
        }

        @Override
        public void lock() {
            try (Jedis jedis = jedisPool.getResource()) {
                if (!lock.acquire(jedis)) {
                    throw new RuntimeException("acquire timeouted");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("lock failed", e);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            try (Jedis jedis = jedisPool.getResource()) {
                if (!lock.acquire(jedis)) {
                    throw new RuntimeException("acquire timeouted");
                }
            }
        }

        @Override
        public boolean tryLock() {
            try (Jedis jedis = jedisPool.getResource()) {
                return lock.acquire(jedis);
            } catch (InterruptedException e) {
                throw new RuntimeException("lock failed", e);
            }
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            try (Jedis jedis = jedisPool.getResource()) {
                return lock.acquire(jedis);
            }
        }

        @Override
        public void unlock() {
            try (Jedis jedis = jedisPool.getResource()) {
                lock.release(jedis);
            }
        }

        @Override
        public Condition newCondition() {
            throw new RuntimeException("unsupported method");
        }
    }
}
