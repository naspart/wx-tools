package com.rolbel.miniapp.api.impl;

import com.rolbel.miniapp.api.WxMaService;
import com.rolbel.miniapp.api.WxMaUserService;
import com.rolbel.miniapp.bean.WxMaJscode2SessionResult;
import com.rolbel.miniapp.bean.WxMaPhoneNumberInfo;
import com.rolbel.miniapp.bean.WxMaUserInfo;
import com.rolbel.miniapp.util.crypt.WxMaCryptUtils;
import com.rolbel.common.exception.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;

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
    return service.jsCode2SessionInfo(jsCode);
  }

  @Override
  public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaUserInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
  }

  @Override
  public WxMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaPhoneNumberInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
  }

  @Override
  public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
    final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
    return generatedSignature.equals(signature);
  }

}
