package com.rolbel.ma.config;

import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import com.rolbel.ma.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.File;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 */
@Data
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
    protected volatile String cardApiTicket;

    /**
     * 临时文件目录
     */
    protected volatile File tmpDirFile;

    protected volatile ApacheHttpClientBuilder apacheHttpClientBuilder;
}
