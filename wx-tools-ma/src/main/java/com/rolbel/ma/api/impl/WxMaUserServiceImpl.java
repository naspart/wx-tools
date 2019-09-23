package com.rolbel.ma.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.SignUtils;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.api.WxMaUserService;
import com.rolbel.ma.bean.WxMaJscode2SessionResult;
import com.rolbel.ma.bean.WxMaPhoneNumberInfo;
import com.rolbel.ma.bean.WxMaUserInfo;
import com.rolbel.ma.config.WxMaConfig;
import com.rolbel.ma.util.crypt.WxMaCryptUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMaUserServiceImpl implements WxMaUserService {
    private final Logger log = LoggerFactory.getLogger(WxMaUserServiceImpl.class);

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
    public void setUserStorage(Map<String, String> kvMap, String sessionKey, String openid) throws WxErrorException {
        final WxMaConfig config = this.service.getWxMaConfig();
        JsonObject param = new JsonObject();
        JsonArray array = new JsonArray();
        for (Map.Entry<String, String> e : kvMap.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", e.getKey());
            jsonObject.addProperty("value", e.getValue());
            array.add(jsonObject);
        }
        param.add("kv_list", array);
        String params = param.toString();
        String signature = SignUtils.createHmacSha256Sign(params, sessionKey);
        String url = String.format("https://api.weixin.qq.com/wxa/set_user_storage" +
                        "?appid=%s&signature=%s&openid=%s&sig_method=%s",
                config.getAppId(), signature, openid, "hmac_sha256");
        String result = this.service.post(url, params);
        WxError error = WxError.fromJson(result);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
    }

    @Override
    public WxMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
        String decryptMsg = WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr);
        this.log.debug("用户手机号解密：" + decryptMsg);
        return WxMaPhoneNumberInfo.fromJson(decryptMsg);
    }

    @Override
    public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
        final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
        return generatedSignature.equals(signature);
    }
}
