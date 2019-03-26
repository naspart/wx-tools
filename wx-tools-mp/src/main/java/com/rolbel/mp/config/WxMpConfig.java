package com.rolbel.mp.config;

import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;

import java.io.File;

/**
 * 微信客户端配置存储
 */
public interface WxMpConfig {
    String getAccessToken();

    String getJsapiTicket();

    String getCardApiTicket();

    String getSdkTicket();

    String getAppId();

    String getSecret();

    String getToken();

    String getAesKey();

    String getTemplateId();

    String getOauth2redirectUri();

    String getHttpProxyHost();

    int getHttpProxyPort();

    String getHttpProxyUsername();

    String getHttpProxyPassword();

    File getTmpDirFile();

    /**
     * http client builder
     *
     * @return ApacheHttpClientBuilder
     */
    ApacheHttpClientBuilder getApacheHttpClientBuilder();
}
