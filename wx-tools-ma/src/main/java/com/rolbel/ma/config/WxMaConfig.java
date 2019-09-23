package com.rolbel.ma.config;

import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;

import java.util.concurrent.locks.Lock;

/**
 * 小程序配置
 */
public interface WxMaConfig {
    String getAccessToken();

    Lock getAccessTokenLock();

    boolean isAccessTokenExpired();

    /**
     * 强制将access token过期掉
     */
    void expireAccessToken();

    /**
     * 应该是线程安全的
     *
     * @param accessToken      新的accessToken值
     * @param expiresInSeconds 过期时间，以秒为单位
     */
    void updateAccessToken(String accessToken, int expiresInSeconds);

    String getJsapiTicket();

    Lock getJsapiTicketLock();

    boolean isJsapiTicketExpired();

    /**
     * 强制将jsapi ticket过期掉
     */
    void expireJsapiTicket();

    /**
     * 应该是线程安全的
     *
     * @param jsapiTicket      新的jsapi ticket值
     * @param expiresInSeconds 过期时间，以秒为单位
     */
    void updateJsapiTicket(String jsapiTicket, int expiresInSeconds);

    String getCardApiTicket();

    Lock getCardApiTicketLock();

    boolean isCardApiTicketExpired();

    /**
     * 强制将卡券api ticket过期掉.
     */
    void expireCardApiTicket();

    /**
     * 应该是线程安全的.
     *
     * @param apiTicket        新的卡券api ticket值
     * @param expiresInSeconds 过期时间，以秒为单位
     */
    void updateCardApiTicket(String apiTicket, int expiresInSeconds);

    String getAppId();

    void setAppId(String appId);

    String getSecret();

    String getToken();

    String getAesKey();

    String getMsgDataFormat();

    String getHttpProxyHost();

    int getHttpProxyPort();

    String getHttpProxyUsername();

    String getHttpProxyPassword();

    /**
     * http client builder
     *
     * @return ApacheHttpClientBuilder
     */
    ApacheHttpClientBuilder getApacheHttpClientBuilder();
}
