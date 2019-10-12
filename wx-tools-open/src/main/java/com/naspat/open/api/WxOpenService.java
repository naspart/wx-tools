package com.naspat.open.api;

import com.naspat.common.error.WxErrorException;
import com.naspat.open.config.WxOpenConfig;

public interface WxOpenService {
    WxOpenComponentService getWxOpenComponentService();

    WxOpenConfig getWxOpenConfig();

    void setWxOpenConfig(WxOpenConfig wxOpenConfig);

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
     */
    String get(String url, String queryParam) throws WxErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
     */
    String post(String url, String postData) throws WxErrorException;
}
