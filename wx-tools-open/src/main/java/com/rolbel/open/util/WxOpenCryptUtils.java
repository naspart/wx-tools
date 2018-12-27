package com.rolbel.open.util;

import com.rolbel.open.config.WxOpenConfig;
import org.apache.commons.codec.binary.Base64;

public class WxOpenCryptUtils extends com.rolbel.common.util.crypto.WxCryptUtil {
    /**
     * 构造函数
     *
     * @param wxOpenConfig
     */
    public WxOpenCryptUtils(WxOpenConfig wxOpenConfig) {
        /*
         * @param token          公众平台上，开发者设置的token
         * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
         * @param appId          公众平台appid
         */
        String encodingAesKey = wxOpenConfig.getComponentAesKey();
        String token = wxOpenConfig.getComponentToken();
        String appId = wxOpenConfig.getComponentAppId();

        this.token = token;
        this.appidOrCorpid = appId;
        this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }
}
