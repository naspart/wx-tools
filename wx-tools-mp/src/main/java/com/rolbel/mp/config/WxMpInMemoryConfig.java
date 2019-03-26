package com.rolbel.mp.config;

import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.File;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 */
@Data
public class WxMpInMemoryConfig implements WxMpConfig {
    protected volatile String appId;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String templateId;
    protected volatile String accessToken;
    protected volatile String aesKey;
    protected volatile long expiresTime;

    protected volatile String oauth2redirectUri;

    protected volatile String httpProxyHost;
    protected volatile int httpProxyPort;
    protected volatile String httpProxyUsername;
    protected volatile String httpProxyPassword;

    protected volatile String jsapiTicket;
    protected volatile String sdkTicket;
    protected volatile String cardApiTicket;

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
    public String getJsapiTicket() {
        return this.jsapiTicket;
    }

    @Override
    public String getCardApiTicket() {
        return this.cardApiTicket;
    }

    @Override
    public String getSdkTicket() {
        return this.sdkTicket;
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
