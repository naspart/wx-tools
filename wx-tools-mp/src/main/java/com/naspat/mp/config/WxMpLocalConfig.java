package com.naspat.mp.config;

import com.naspat.common.WxType;
import com.naspat.common.bean.WxAccessToken;
import com.naspat.common.bean.WxTicket;
import com.naspat.common.enums.TicketType;
import com.naspat.common.error.WxError;
import com.naspat.common.util.http.DefaultHttpClientBuilder;
import com.naspat.common.util.http.HttpClientBuilder;
import com.naspat.mp.bean.WxMpHostConfig;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.Getter;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.naspat.mp.enums.WxMpApiUrl.Other.GET_ACCESS_TOKEN_URL;
import static com.naspat.mp.enums.WxMpApiUrl.Other.GET_TICKET_URL;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 */
public class WxMpLocalConfig implements WxMpConfig {
    @Getter
    protected volatile String appId;

    @Getter
    protected volatile String secret;

    @Getter
    protected volatile String token;

    @Getter
    protected volatile String templateId;

    @Getter
    protected volatile String aesKey;

    @Getter
    protected volatile String httpProxyHost;

    @Getter
    protected volatile int httpProxyPort;

    @Getter
    protected volatile String httpProxyUsername;

    @Getter
    protected volatile String httpProxyPassword;

    protected volatile String accessToken;
    protected volatile long accessTokenExpiresTime;

    protected volatile String jsapiTicket;
    protected volatile long jsapiTicketExpiresTime;

    protected volatile String sdkTicket;
    protected volatile long sdkTicketExpiresTime;

    protected volatile String cardApiTicket;
    protected volatile long cardApiTicketExpiresTime;

    protected Lock accessTokenLock = new ReentrantLock();
    protected Lock jsApiTicketLock = new ReentrantLock();
    protected Lock sdkTicketLock = new ReentrantLock();
    protected Lock cardApiTicketLock = new ReentrantLock();

    @Getter
    protected volatile File tmpDirFile;

    protected volatile CloseableHttpClient httpClient;
    protected volatile HttpHost httpProxy;

    public static WxMpLocalConfig.Builder builder() {
        return new WxMpLocalConfig.Builder();
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

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder aesKey(String aesKey) {
            this.aesKey = aesKey;
            return this;
        }

        public Builder httpProxyHost(String httpProxyHost) {
            this.httpProxyHost = httpProxyHost;
            return this;
        }

        public Builder httpProxyPort(int httpProxyPort) {
            this.httpProxyPort = httpProxyPort;
            return this;
        }

        public Builder httpProxyUsername(String httpProxyUsername) {
            this.httpProxyUsername = httpProxyUsername;
            return this;
        }

        public Builder httpProxyPassword(String httpProxyPassword) {
            this.httpProxyPassword = httpProxyPassword;
            return this;
        }

        public WxMpLocalConfig build() {
            return new WxMpLocalConfig(this);
        }
    }

    private WxMpLocalConfig(Builder builder) {
        appId = builder.appId;
        secret = builder.secret;
        token = builder.token;
        templateId = builder.templateId;
        aesKey = builder.aesKey;

        httpProxyHost = builder.httpProxyHost;
        httpProxyPort = builder.httpProxyPort;
        httpProxyUsername = builder.httpProxyUsername;
        httpProxyPassword = builder.httpProxyPassword;

        initHttp();
    }

    private boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.accessTokenExpiresTime;
    }

    private void initHttp() {
        HttpClientBuilder httpClientBuilder = DefaultHttpClientBuilder.get();

        httpClientBuilder.httpProxyHost(this.getHttpProxyHost())
                .httpProxyPort(this.getHttpProxyPort())
                .httpProxyUsername(this.getHttpProxyUsername())
                .httpProxyPassword(this.getHttpProxyPassword());

        if (this.getHttpProxyHost() != null && this.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(this.getHttpProxyHost(), this.getHttpProxyPort());
        }

        this.httpClient = httpClientBuilder.build();
    }

    @Override
    public String getAccessToken() {
        if (!this.isAccessTokenExpired()) {
            return this.accessToken;
        }

        Lock lock = this.accessTokenLock;
        try {
            lock.lock();

            String url = String.format(GET_ACCESS_TOKEN_URL.getUrl(this), this.getAppId(), this.getSecret());
            try {
                HttpGet httpGet = new HttpGet(url);
                if (this.httpProxyHost != null) {
                    RequestConfig requestConfig = RequestConfig.custom().setProxy(this.httpProxy).build();
                    httpGet.setConfig(requestConfig);
                }
                try (CloseableHttpResponse response = this.httpClient.execute(httpGet)) {
                    String resultContent = new BasicResponseHandler().handleResponse(response);
                    WxError error = WxError.fromJson(resultContent, WxType.MP);

                    if (error.getErrorCode() != 0) {
                        return null;
                    }
                    WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                    this.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());

                    return this.accessToken;
                } finally {
                    httpGet.releaseConnection();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getTicket(TicketType type) {
        if (!this.isTicketExpired(type)) {
            return this.getTicketVal(type);
        }

        Lock lock = this.getTicketLock(type);
        try {
            lock.lock();

            String url = String.format(GET_TICKET_URL.getUrl(this), type.getCode());
            try {
                HttpGet httpGet = new HttpGet(url);
                if (this.httpProxyHost != null) {
                    RequestConfig requestConfig = RequestConfig.custom().setProxy(this.httpProxy).build();
                    httpGet.setConfig(requestConfig);
                }
                try (CloseableHttpResponse response = this.httpClient.execute(httpGet)) {
                    String resultContent = new BasicResponseHandler().handleResponse(response);
                    WxError error = WxError.fromJson(resultContent, WxType.MP);

                    if (error.getErrorCode() != 0) {
                        return null;
                    }

                    WxTicket wxTicket = WxTicket.fromJson(resultContent);
                    this.updateTicket(type, wxTicket.getTicket(), wxTicket.getExpiresIn());

                    return this.getTicketVal(type);
                } finally {
                    httpGet.releaseConnection();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }

    private synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        this.accessTokenExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    private String getTicketVal(TicketType type) {
        switch (type) {
            case SDK:
                return this.sdkTicket;
            case JSAPI:
                return this.jsapiTicket;
            case WX_CARD:
                return this.cardApiTicket;
            default:
                return null;
        }
    }

    private Lock getTicketLock(TicketType type) {
        switch (type) {
            case SDK:
                return this.sdkTicketLock;
            case JSAPI:
                return this.jsApiTicketLock;
            case WX_CARD:
                return this.cardApiTicketLock;
            default:
                return null;
        }
    }

    private boolean isTicketExpired(TicketType type) {
        switch (type) {
            case SDK:
                return System.currentTimeMillis() > this.sdkTicketExpiresTime;
            case JSAPI:
                return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
            case WX_CARD:
                return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
            default:
                return false;
        }
    }

    private synchronized void updateTicket(TicketType type, String ticket, int expiresInSeconds) {
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
            case SDK:
                this.sdkTicket = ticket;
                // 预留200秒的时间
                this.sdkTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
                break;
            default:
        }
    }

    @Override
    public WxMpHostConfig getHostConfig() {
        return null;
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
