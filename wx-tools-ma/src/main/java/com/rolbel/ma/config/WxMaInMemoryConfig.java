package com.rolbel.ma.config;

import com.rolbel.common.bean.WxAccessToken;
import com.rolbel.common.enums.TicketType;
import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import com.rolbel.ma.util.json.WxMaGsonBuilder;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 */
public class WxMaInMemoryConfig implements WxMaConfig {
    protected volatile String msgDataFormat;
    protected volatile String appId;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String accessToken;
    protected volatile String aesKey;
    protected volatile long expiresTime;

    protected volatile String httpProxyHost;
    protected volatile int httpProxyPort;
    protected volatile String httpProxyUsername;
    protected volatile String httpProxyPassword;

    protected volatile String jsapiTicket;
    protected volatile long jsapiTicketExpiresTime;

    //微信卡券的ticket单独缓存
    protected volatile String cardApiTicket;
    protected volatile long cardApiTicketExpiresTime;


    protected Lock accessTokenLock = new ReentrantLock();
    protected Lock jsapiTicketLock = new ReentrantLock();
    protected Lock cardApiTicketLock = new ReentrantLock();

    /**
     * 临时文件目录
     */
    protected volatile File tmpDirFile;

    protected volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public synchronized void updateAccessToken(WxAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public String getTicket(TicketType type) {
        switch (type) {
            case JSAPI:
                return this.jsapiTicket;
            case WX_CARD:
                return this.cardApiTicket;
            default:
                return null;
        }
    }

    @Override
    public Lock getTicketLock(TicketType type) {
        switch (type) {
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
        switch (type) {
            case JSAPI:
                return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
            case WX_CARD:
                return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
            default:
                return false;
        }
    }

    @Override
    public synchronized void updateTicket(TicketType type, String ticket, int expiresInSeconds) {
        switch (type) {
            case JSAPI:
                this.jsapiTicket = ticket;
                // 预留200秒的时间
                this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
                break;
            case WX_CARD:
                this.cardApiTicket = ticket;
                // 预留200秒的时间
                this.cardApiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
                break;
            default:
        }
    }

    @Override
    public void expireTicket(TicketType type) {
        switch (type) {
            case JSAPI:
                this.jsapiTicketExpiresTime = 0;
                break;
            case WX_CARD:
                this.cardApiTicketExpiresTime = 0;
                break;
            default:
        }
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
    public long getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
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

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public String toString() {
        return WxMaGsonBuilder.create().toJson(this);
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return this.apacheHttpClientBuilder;
    }

    public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
