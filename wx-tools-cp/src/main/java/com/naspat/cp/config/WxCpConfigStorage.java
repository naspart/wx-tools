package com.naspat.cp.config;

import com.naspat.common.bean.WxAccessToken;
import com.naspat.common.util.http.HttpClientBuilder;

import java.io.File;

/**
 * 微信客户端配置存储
 */
public interface WxCpConfigStorage {

  String getAccessToken();

  boolean isAccessTokenExpired();

  /**
   * 强制将access token过期掉
   */
  void expireAccessToken();

  void updateAccessToken(WxAccessToken accessToken);

  void updateAccessToken(String accessToken, int expiresIn);

  String getJsapiTicket();

  boolean isJsapiTicketExpired();

  /**
   * 强制将jsapi ticket过期掉
   */
  void expireJsapiTicket();

  /**
   * 应该是线程安全的
   *
   * @param jsapiTicket
   */
  void updateJsapiTicket(String jsapiTicket, int expiresInSeconds);

  String getCorpId();

  String getCorpSecret();

  Integer getAgentId();

  String getToken();

  String getAesKey();

  long getExpiresTime();

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
  HttpClientBuilder getHttpClientBuilder();
}
