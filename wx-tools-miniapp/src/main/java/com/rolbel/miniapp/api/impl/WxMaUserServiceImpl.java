package com.rolbel.miniapp.api.impl;

import com.rolbel.miniapp.api.WxMaService;
import com.rolbel.miniapp.api.WxMaUserService;
import com.rolbel.miniapp.bean.WxMaJscode2SessionResult;
import com.rolbel.miniapp.bean.WxMaUserInfo;
import com.rolbel.miniapp.config.WxMaConfig;
import com.rolbel.miniapp.util.crypt.WxMaCryptUtils;
import com.google.common.base.Joiner;
import com.rolbel.common.exception.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMaUserServiceImpl implements WxMaUserService {
  private WxMaService service;

  public WxMaUserServiceImpl(WxMaService service) {
    this.service = service;
  }

  @Override
  public WxMaJscode2SessionResult getSessionInfo(String jsCode) throws WxErrorException {
    final WxMaConfig config = service.getWxMaConfig();
    Map<String, String> params = new HashMap<>(8);
    params.put("appid", config.getAppid());
    params.put("secret", config.getSecret());
    params.put("js_code", jsCode);
    params.put("grant_type", "authorization_code");

    String result = this.service.get(JSCODE_TO_SESSION_URL, Joiner.on("&").withKeyValueSeparator("=").join(params));
    return WxMaJscode2SessionResult.fromJson(result);
  }

  @Override
  public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaUserInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
  }

  @Override
  public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
    final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
    System.out.println(generatedSignature);
    return generatedSignature.equals(signature);
  }

}
