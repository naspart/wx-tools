/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package com.rolbel.mp.util.crypto;

import com.rolbel.common.util.crypto.WxCryptUtil;
import com.rolbel.mp.config.WxMpConfig;
import org.apache.commons.codec.binary.Base64;

public class WxMpCryptUtils extends WxCryptUtil {

    /**
     * 构造函数
     *
     * @param wxMpConfig 微信公众号配置
     */
    public WxMpCryptUtils(WxMpConfig wxMpConfig) {
        /*
         * @param token          公众平台上，开发者设置的token
         * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
         * @param appId          公众平台appid
         */
        String encodingAesKey = wxMpConfig.getAesKey();
        String token = wxMpConfig.getToken();
        String appId = wxMpConfig.getAppId();

        this.token = token;
        this.appidOrCorpid = appId;
        this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }
}
