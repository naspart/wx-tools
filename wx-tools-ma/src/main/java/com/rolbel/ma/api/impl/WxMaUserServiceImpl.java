package com.rolbel.ma.api.impl;

import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.api.WxMaUserService;
import com.rolbel.ma.bean.WxMaJscode2SessionResult;
import com.rolbel.ma.bean.WxMaPhoneNumberInfo;
import com.rolbel.ma.bean.WxMaUserInfo;
import com.rolbel.ma.util.crypt.WxMaCryptUtils;
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
