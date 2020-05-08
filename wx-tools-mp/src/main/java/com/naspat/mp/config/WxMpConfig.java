package com.naspat.mp.config;

import com.naspat.common.enums.TicketType;
import com.naspat.mp.bean.WxMpHostConfig;

import java.io.File;

/**
 * 微信客户端配置存储
 */
public interface WxMpConfig {
    String getAccessToken();

    String getTicket(TicketType type);

    String getAppId();

    String getSecret();

    String getToken();

    String getAesKey();

    String getTemplateId();

    String getHttpProxyHost();

    int getHttpProxyPort();

    String getHttpProxyUsername();

    String getHttpProxyPassword();


    File getTmpDirFile();

    /**
     * 得到微信接口地址域名部分的自定义设置信息.
     */
    WxMpHostConfig getHostConfig();
}
