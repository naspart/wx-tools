package com.rolbel.open.util;

import com.rolbel.open.api.WxOpenConfigStorage;
import org.apache.commons.codec.binary.Base64;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenCryptUtils extends com.rolbel.common.util.crypto.WxCryptUtil {
  /**
   * 构造函数
   *
   * @param wxOpenConfigStorage
   */
  public WxOpenCryptUtils(WxOpenConfigStorage wxOpenConfigStorage) {
    /*
     * @param token          公众平台上，开发者设置的token
     * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
     * @param appId          公众平台appid
     */
    String encodingAesKey = wxOpenConfigStorage.getComponentAesKey();
    String token = wxOpenConfigStorage.getComponentToken();
    String appId = wxOpenConfigStorage.getComponentAppId();

    this.token = token;
    this.appidOrCorpid = appId;
    this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
  }
}