package com.naspat.open.config;


import com.naspat.common.enums.TicketType;
import com.naspat.common.util.http.HttpClientBuilder;
import com.naspat.ma.config.WxMaConfig;
import com.naspat.mp.bean.WxMpHostConfig;
import com.naspat.mp.config.WxMpConfig;
import com.naspat.open.bean.WxOpenAuthorizerAccessToken;
import com.naspat.open.bean.WxOpenComponentAccessToken;
import com.naspat.open.util.json.WxOpenGsonBuilder;
import com.naspat.pay.config.WxPayConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 */
public class WxOpenInMemoryConfig implements WxOpenConfig {
    private String componentAppId;
    private String componentAppSecret;
    private String componentToken;
    private String componentAesKey;
    private String componentVerifyTicket;
    private String componentAccessToken;
    private long componentExpiresTime;

    private String httpProxyHost;
    private int httpProxyPort;
    private String httpProxyUsername;
    private String httpProxyPassword;
    private HttpClientBuilder httpClientBuilder;

    private Lock componentAccessTokenLock = new ReentrantLock();

    private static final Map<String, Lock> authorizerAccessTokenLocks = new HashMap<>();

    private Map<String, Token> authorizerRefreshTokens = new Hashtable<>();
    private Map<String, Token> authorizerAccessTokens = new Hashtable<>();
    private Map<String, Token> jsapiTickets = new Hashtable<>();
    private Map<String, Token> cardApiTickets = new Hashtable<>();

    @Override
    public String getComponentAppId() {
        return componentAppId;
    }

    @Override
    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    @Override
    public String getComponentAppSecret() {
        return componentAppSecret;
    }

    @Override
    public void setComponentAppSecret(String componentAppSecret) {
        this.componentAppSecret = componentAppSecret;
    }

    @Override
    public String getComponentToken() {
        return componentToken;
    }

    @Override
    public void setComponentToken(String componentToken) {
        this.componentToken = componentToken;
    }

    @Override
    public String getComponentAesKey() {
        return componentAesKey;
    }

    @Override
    public void setComponentAesKey(String componentAesKey) {
        this.componentAesKey = componentAesKey;
    }

    @Override
    public String getComponentVerifyTicket() {
        return componentVerifyTicket;
    }

    @Override
    public void setComponentVerifyTicket(String componentVerifyTicket) {
        this.componentVerifyTicket = componentVerifyTicket;
    }

    @Override
    public String getComponentAccessToken() {
        return componentAccessToken;
    }

    @Override
    public Lock getComponentAccessTokenLock() {
        return this.componentAccessTokenLock;
    }

    @Override
    public boolean isComponentAccessTokenExpired() {
        return System.currentTimeMillis() > componentExpiresTime;
    }

    @Override
    public void expireComponentAccessToken() {
        this.componentExpiresTime = 0L;
    }

    @Override
    public void updateComponentAccessTokent(WxOpenComponentAccessToken componentAccessToken) {
        updateComponentAccessTokent(componentAccessToken.getComponentAccessToken(), componentAccessToken.getExpiresIn());
    }

    @Override
    public void updateComponentAccessTokent(String componentAccessToken, int expiresInSeconds) {
        this.componentAccessToken = componentAccessToken;
        this.componentExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getHttpProxyHost() {
        return httpProxyHost;
    }

    @Override
    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override
    public int getHttpProxyPort() {
        return httpProxyPort;
    }

    @Override
    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override
    public String getHttpProxyUsername() {
        return httpProxyUsername;
    }

    @Override
    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    @Override
    public String getHttpProxyPassword() {
        return httpProxyPassword;
    }

    @Override
    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public HttpClientBuilder getHttpClientBuilder() {
        return httpClientBuilder;
    }

    @Override
    public HttpClientBuilder setApacheHttpClientBuilder(HttpClientBuilder httpClientBuilder) {
        return this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public WxMpConfig getWxMpConfig(String appId) {
        return new WxOpenInnerConfig(this, appId);
    }

    @Override
    public WxMaConfig getWxMaConfig(String appId) {
        return new WxOpenInnerConfig(this, appId);
    }

    @Override
    public WxPayConfig getWxPayConfig(String appId) {
        return null;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    private String getTokenString(Map<String, Token> map, String key) {
        Token token = map.get(key);
        if (token == null || (token.expiresTime != null && System.currentTimeMillis() > token.expiresTime)) {
            return null;
        }
        return token.token;
    }

    private void expireToken(Map<String, Token> map, String key) {
        Token token = map.get(key);
        if (token != null) {
            token.expiresTime = 0L;
        }
    }

    private void updateToken(Map<String, Token> map, String key, String tokenString, Integer expiresInSeconds) {
        Token token = map.get(key);
        if (token == null) {
            token = new Token();
            map.put(key, token);
        }

        token.token = tokenString;
        if (expiresInSeconds != null) {
            token.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
        }
    }

    @Override
    public String getAuthorizerRefreshToken(String appId) {
        return getTokenString(authorizerRefreshTokens, appId);
    }

    @Override
    public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
        updateToken(authorizerRefreshTokens, appId, authorizerRefreshToken, null);
    }

    @Override
    public String getAuthorizerAccessToken(String appId) {
        return getTokenString(authorizerAccessTokens, appId);
    }

    @Override
    public Lock getAuthorizerAccessTokenLock(String appId) {
        Lock authorizerAccessTokenLock = authorizerAccessTokenLocks.get(appId);
        if (authorizerAccessTokenLock == null) {
            synchronized (authorizerAccessTokenLocks) {
                authorizerAccessTokenLock = authorizerAccessTokenLocks.get(appId);
                if (authorizerAccessTokenLock == null) {
                    authorizerAccessTokenLock = new ReentrantLock();

                    authorizerAccessTokenLocks.put(appId, authorizerAccessTokenLock);
                }
            }
        }

        return authorizerAccessTokenLock;
    }


    @Override
    public boolean isAuthorizerAccessTokenExpired(String appId) {
        return getTokenString(authorizerAccessTokens, appId) == null;
    }

    @Override
    public void expireAuthorizerAccessToken(String appId) {
        expireToken(authorizerAccessTokens, appId);
    }

    @Override
    public void updateAuthorizerAccessToken(String appId, WxOpenAuthorizerAccessToken authorizerAccessToken) {
        updateAuthorizerAccessToken(appId, authorizerAccessToken.getAuthorizerAccessToken(), authorizerAccessToken.getExpiresIn());
    }

    @Override
    public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken, int expiresInSeconds) {
        updateToken(authorizerAccessTokens, appId, authorizerAccessToken, expiresInSeconds);
    }

    @Override
    public String getJsapiTicket(String appId) {
        return getTokenString(jsapiTickets, appId);
    }

    @Override
    public boolean isJsapiTicketExpired(String appId) {
        return getTokenString(jsapiTickets, appId) == null;
    }

    @Override
    public void expireJsapiTicket(String appId) {
        expireToken(jsapiTickets, appId);
    }

    @Override
    public void updateJsapiTicket(String appId, String jsapiTicket, int expiresInSeconds) {
        updateToken(jsapiTickets, appId, jsapiTicket, expiresInSeconds);
    }

    @Override
    public String getCardApiTicket(String appId) {
        return getTokenString(cardApiTickets, appId);
    }

    @Override
    public boolean isCardApiTicketExpired(String appId) {
        return getTokenString(cardApiTickets, appId) == null;
    }

    @Override
    public void expireCardApiTicket(String appId) {
        expireToken(cardApiTickets, appId);
    }

    @Override
    public void updateCardApiTicket(String appId, String cardApiTicket, int expiresInSeconds) {
        updateToken(cardApiTickets, appId, cardApiTicket, expiresInSeconds);
    }

    private static class Token {
        private String token;
        private Long expiresTime;
    }

    private static class WxOpenInnerConfig implements WxMpConfig, WxMaConfig {
        private WxOpenConfig wxOpenConfig;
        private String appId;

        private Lock accessTokenLock = new ReentrantLock();
        private Lock jsapiTicketLock = new ReentrantLock();
        private Lock cardApiTicketLock = new ReentrantLock();

        private WxOpenInnerConfig(WxOpenConfig wxOpenConfig, String appId) {
            this.wxOpenConfig = wxOpenConfig;
            this.appId = appId;
        }

        @Override
        public String getAccessToken() {
            return wxOpenConfig.getAuthorizerAccessToken(appId);
        }

        @Override
        public Lock getAccessTokenLock() {
            return this.accessTokenLock;
        }

        @Override
        public boolean isAccessTokenExpired() {
            return wxOpenConfig.isAuthorizerAccessTokenExpired(appId);
        }

        @Override
        public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
            wxOpenConfig.updateAuthorizerAccessToken(appId, accessToken, expiresInSeconds);
        }

        @Override
        public String getTicket(TicketType type) {
            switch (type) {
                case JSAPI: {
                    return wxOpenConfig.getJsapiTicket(appId);
                }
                case WX_CARD: {
                    return wxOpenConfig.getCardApiTicket(appId);
                }
                default: {
                    // do nothing
                }
            }
            return null;
        }

        public Lock getTicketLock(TicketType type) {
            switch (type) {
                case JSAPI: {
                    return this.jsapiTicketLock;
                }
                case WX_CARD: {
                    return this.cardApiTicketLock;
                }
                default: {
                    // do nothing
                }
            }
            return null;
        }

        public boolean isTicketExpired(TicketType type) {
            switch (type) {
                case JSAPI: {
                    return wxOpenConfig.isJsapiTicketExpired(appId);
                }
                case WX_CARD: {
                    return wxOpenConfig.isCardApiTicketExpired(appId);
                }
                default: {
                    // do nothing
                }
            }

            return false;
        }

        public void expireTicket(TicketType type) {
            switch (type) {
                case JSAPI: {
                    wxOpenConfig.expireJsapiTicket(appId);
                    break;
                }
                case WX_CARD: {
                    wxOpenConfig.expireCardApiTicket(appId);
                    break;
                }
                default: {
                    // do nothing
                }
            }
        }

        public void updateTicket(TicketType type, String ticket, int expiresInSeconds) {
            switch (type) {
                case JSAPI: {
                    wxOpenConfig.updateJsapiTicket(appId, ticket, expiresInSeconds);
                    break;
                }
                case WX_CARD: {
                    wxOpenConfig.updateCardApiTicket(appId, ticket, expiresInSeconds);
                    break;
                }
                default: {
                    // do nothing
                }
            }

        }

        @Override
        public void expireAccessToken() {
            wxOpenConfig.expireAuthorizerAccessToken(appId);
        }

        @Override
        public String getJsapiTicket() {
            return wxOpenConfig.getJsapiTicket(appId);
        }

        @Override
        public Lock getJsapiTicketLock() {
            return this.jsapiTicketLock;
        }

        @Override
        public boolean isJsapiTicketExpired() {
            return wxOpenConfig.isJsapiTicketExpired(appId);
        }

        @Override
        public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
            wxOpenConfig.updateJsapiTicket(appId, jsapiTicket, expiresInSeconds);
        }

        @Override
        public void expireJsapiTicket() {
            wxOpenConfig.expireJsapiTicket(appId);
        }

        @Override
        public String getCardApiTicket() {
            return wxOpenConfig.getCardApiTicket(appId);
        }

        @Override
        public Lock getCardApiTicketLock() {
            return this.cardApiTicketLock;
        }

        @Override
        public boolean isCardApiTicketExpired() {
            return wxOpenConfig.isCardApiTicketExpired(appId);
        }

        @Override
        public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
            wxOpenConfig.updateCardApiTicket(appId, cardApiTicket, expiresInSeconds);
        }

        @Override
        public void expireCardApiTicket() {
            wxOpenConfig.expireCardApiTicket(appId);
        }

        @Override
        public String getAppId() {
            return this.appId;
        }

        @Override
        public void setAppId(String appId) {
            this.appId = appId;
        }

        @Override
        public String getSecret() {
            return null;
        }

        @Override
        public String getToken() {
            return wxOpenConfig.getComponentToken();
        }

        @Override
        public String getTemplateId() {
            return null;
        }

        @Override
        public String getAesKey() {
            return wxOpenConfig.getComponentAesKey();
        }

        @Override
        public String getMsgDataFormat() {
            return null;
        }

        @Override
        public String getHttpProxyHost() {
            return this.wxOpenConfig.getHttpProxyHost();
        }

        @Override
        public int getHttpProxyPort() {
            return this.wxOpenConfig.getHttpProxyPort();
        }

        @Override
        public String getHttpProxyUsername() {
            return this.wxOpenConfig.getHttpProxyUsername();
        }

        @Override
        public String getHttpProxyPassword() {
            return this.wxOpenConfig.getHttpProxyPassword();
        }

        @Override
        public File getTmpDirFile() {
            return null;
        }

        @Override
        public String toString() {
            return WxOpenGsonBuilder.create().toJson(this);
        }

        @Override
        public HttpClientBuilder getHttpClientBuilder() {
            return wxOpenConfig.getHttpClientBuilder();
        }

        @Override
        public WxMpHostConfig getHostConfig() {
            return null;
        }
    }
}
