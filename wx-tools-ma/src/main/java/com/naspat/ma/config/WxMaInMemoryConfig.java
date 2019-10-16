package com.naspat.ma.config;

import com.naspat.common.util.http.HttpClientBuilder;

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
    protected volatile String aesKey;

    protected volatile String httpProxyHost;
    protected volatile int httpProxyPort;
    protected volatile String httpProxyUsername;
    protected volatile String httpProxyPassword;

    protected volatile String accessToken;
    private volatile long accessTokenExpiresTime;

    protected volatile String jsapiTicket;
    private volatile long jsapiTicketExpiresTime;

    protected volatile String cardApiTicket;
    private volatile long cardApiTicketExpiresTime;

    protected Lock accessTokenLock = new ReentrantLock();
    private Lock jsapiTicketLock = new ReentrantLock();
    private Lock cardApiTicketLock = new ReentrantLock();

    protected volatile File tmpDirFile;

    protected volatile HttpClientBuilder httpClientBuilder;

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    public void setAccessTokenLock(Lock accessTokenLock) {
        this.accessTokenLock = accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.accessTokenExpiresTime;
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        this.accessTokenExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getJsapiTicket() {
        return this.jsapiTicket;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return this.jsapiTicketLock;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
    }

    @Override
    public void expireJsapiTicket() {
        this.jsapiTicketExpiresTime = 0;
    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        this.jsapiTicket = jsapiTicket;
        // 预留200秒的时间
        this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getCardApiTicket() {
        return this.cardApiTicket;
    }

    @Override
    public Lock getCardApiTicketLock() {
        return this.cardApiTicketLock;
    }

    @Override
    public boolean isCardApiTicketExpired() {
        return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
    }

    @Override
    public void expireCardApiTicket() {
        this.cardApiTicketExpiresTime = 0;
    }

    @Override
    public void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        this.cardApiTicket = cardApiTicket;
        // 预留200秒的时间
        this.cardApiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public void expireAccessToken() {
        this.accessTokenExpiresTime = 0;
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

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public HttpClientBuilder getHttpClientBuilder() {
        return this.httpClientBuilder;
    }

    public void setHttpClientBuilder(HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public void setAppId(String appId) {
        this.appId = appId;
    }
}
