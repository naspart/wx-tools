package com.rolbel.ma.config;

import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;

/**
 * 小程序配置
 */
public interface WxMaConfig {
    String getAccessToken();

    String getJsapiTicket();

    String getCardApiTicket();

    String getAppId();

    String getSecret();

    String getToken();

    String getAesKey();

    String getMsgDataFormat();

    long getExpiresTime();

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
