package com.rolbel.open.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.crypto.SHA1;
import com.rolbel.common.util.http.URIUtil;
import com.rolbel.common.util.json.WxGsonBuilder;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.bean.WxMaJscode2SessionResult;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.WxMpOAuth2AccessToken;
import com.rolbel.open.api.WxOpenComponentService;
import com.rolbel.open.api.WxOpenService;
import com.rolbel.open.bean.WxOpenAuthorizerAccessToken;
import com.rolbel.open.bean.WxOpenComponentAccessToken;
import com.rolbel.open.bean.WxOpenMaCodeTemplate;
import com.rolbel.open.bean.auth.WxOpenAuthorizationInfo;
import com.rolbel.open.bean.message.WxOpenXmlMessage;
import com.rolbel.open.bean.result.WxOpenAuthorizerInfoResult;
import com.rolbel.open.bean.result.WxOpenAuthorizerOptionResult;
import com.rolbel.open.bean.result.WxOpenQueryAuthResult;
import com.rolbel.open.config.WxOpenConfig;
import com.rolbel.open.util.json.WxOpenGsonBuilder;
import com.rolbel.pay.api.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class WxOpenComponentServiceImpl implements WxOpenComponentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final Map<String, WxMaService> WX_OPEN_MA_SERVICE_MAP = new HashMap<>();
    private static final Map<String, WxMpService> WX_OPEN_MP_SERVICE_MAP = new HashMap<>();
    private static final Map<String, WxPayService> WX_OPEN_PAY_SERVICE_MAP = new HashMap<>();

    private WxOpenService wxOpenService;

    WxOpenComponentServiceImpl(WxOpenService wxOpenService) {
        this.wxOpenService = wxOpenService;
    }

    @Override
    public WxMpService getWxMpServiceByAppId(String appId) {
        WxMpService wxMpService = WX_OPEN_MP_SERVICE_MAP.get(appId);
        if (wxMpService == null) {
            synchronized (WX_OPEN_MP_SERVICE_MAP) {
                wxMpService = WX_OPEN_MP_SERVICE_MAP.get(appId);
                if (wxMpService == null) {
                    wxMpService = new WxOpenMpServiceImpl(this, appId, getWxOpenConfig().getWxMpConfig(appId));

                    WX_OPEN_MP_SERVICE_MAP.put(appId, wxMpService);
                }
            }
        }

        return wxMpService;
    }

    @Override
    public WxMaService getWxMaServiceByAppId(String appId) {
        WxMaService wxMaService = WX_OPEN_MA_SERVICE_MAP.get(appId);
        if (wxMaService == null) {
            synchronized (WX_OPEN_MA_SERVICE_MAP) {
                wxMaService = WX_OPEN_MA_SERVICE_MAP.get(appId);
                if (wxMaService == null) {
                    wxMaService = new WxOpenMaServiceImpl(this, appId, getWxOpenConfig().getWxMaConfig(appId));
                    WX_OPEN_MA_SERVICE_MAP.put(appId, wxMaService);
                }
            }
        }

        return wxMaService;
    }

    @Override
    public WxPayService getWxPayServiceByAppId(String appId) {
        WxPayService wxPayService = WX_OPEN_PAY_SERVICE_MAP.get(appId);
        if (wxPayService == null) {
            synchronized (WX_OPEN_PAY_SERVICE_MAP) {
                wxPayService = WX_OPEN_PAY_SERVICE_MAP.get(appId);
                if (wxPayService == null) {
                    wxPayService = new WxOpenPayServiceImpl(getWxOpenConfig().getWxPayConfig(appId));

                    WX_OPEN_PAY_SERVICE_MAP.put(appId, wxPayService);
                }
            }
        }

        return wxPayService;
    }

    public WxOpenService getWxOpenService() {
        return wxOpenService;
    }

    @Override
    public WxOpenConfig getWxOpenConfig() {
        return wxOpenService.getWxOpenConfig();
    }

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(this.getWxOpenConfig().getComponentToken(), timestamp, nonce)
                    .equals(signature);
        } catch (Exception e) {
            this.logger.error("Checking signature failed, and the reason is :" + e.getMessage());
            return false;
        }
    }

    @Override
    public String getComponentAccessToken(boolean forceRefresh) throws WxErrorException {
        Lock lock = this.getWxOpenConfig().getComponentAccessTokenLock();
        try {
            lock.lock();

            if (this.getWxOpenConfig().isComponentAccessTokenExpired() || forceRefresh) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
                jsonObject.addProperty("component_appsecret", this.getWxOpenConfig().getComponentAppSecret());
                jsonObject.addProperty("component_verify_ticket", this.getWxOpenConfig().getComponentVerifyTicket());

                String responseContent = this.getWxOpenService().post(API_COMPONENT_TOKEN_URL, jsonObject.toString());
                WxOpenComponentAccessToken componentAccessToken = WxOpenComponentAccessToken.fromJson(responseContent);
                this.getWxOpenConfig().updateComponentAccessTokent(componentAccessToken);
            }
        } finally {
            lock.unlock();
        }

        return this.getWxOpenConfig().getComponentAccessToken();
    }

    private String post(String uri, String postData) throws WxErrorException {
        return post(uri, postData, "component_access_token");
    }

    private String post(String uri, String postData, String accessTokenKey) throws WxErrorException {
        String componentAccessToken = getComponentAccessToken(false);
        String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + accessTokenKey + "=" + componentAccessToken;
        try {
            return getWxOpenService().post(uriWithComponentAccessToken, postData);
        } catch (WxErrorException e) {
            WxError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token
             * 40001 获取access_token时AppSecret错误，或者access_token无效
             * 42001 access_token超时
             * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
             */
            if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001 || error.getErrorCode() == 40014) {
                // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
                this.getWxOpenConfig().expireComponentAccessToken();
                if (this.getWxOpenConfig().autoRefreshToken()) {
                    return this.post(uri, postData, accessTokenKey);
                }
            }
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error, e);
            }
            return null;
        }
    }

    private String get(String uri) throws WxErrorException {
        return get(uri, "component_access_token");
    }

    private String get(String uri, String accessTokenKey) throws WxErrorException {
        String componentAccessToken = getComponentAccessToken(false);
        String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + accessTokenKey + "=" + componentAccessToken;
        try {
            return getWxOpenService().get(uriWithComponentAccessToken, null);
        } catch (WxErrorException e) {
            WxError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token
             * 40001 获取access_token时AppSecret错误，或者access_token无效
             * 42001 access_token超时
             * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
             */
            if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001 || error.getErrorCode() == 40014) {
                // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
                this.getWxOpenConfig().expireComponentAccessToken();
                if (this.getWxOpenConfig().autoRefreshToken()) {
                    return this.get(uri, accessTokenKey);
                }
            }
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error, e);
            }
            return null;
        }
    }

    @Override
    public String getPreAuthUrl(String redirectURI) throws WxErrorException {
        return getPreAuthUrl(redirectURI, null, null);
    }

    @Override
    public String getPreAuthUrl(String redirectURI, String authType, String bizAppid) throws WxErrorException {
        return createPreAuthUrl(redirectURI, authType, bizAppid, false);
    }

    @Override
    public String getMobilePreAuthUrl(String redirectURI) throws WxErrorException {
        return getMobilePreAuthUrl(redirectURI, null, null);
    }

    @Override
    public String getMobilePreAuthUrl(String redirectURI, String authType, String bizAppid) throws WxErrorException {
        return createPreAuthUrl(redirectURI, authType, bizAppid, true);
    }

    /**
     * 创建预授权链接
     *
     * @param redirectURI
     * @param authType
     * @param bizAppid
     * @param isMobile    是否移动端预授权
     * @return
     * @throws WxErrorException
     */
    private String createPreAuthUrl(String redirectURI, String authType, String bizAppid, boolean isMobile) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
        String responseContent = post(API_CREATE_PREAUTHCODE_URL, jsonObject.toString());
        jsonObject = WxGsonBuilder.create().fromJson(responseContent, JsonObject.class);

        String preAuthUrlStr = String.format((isMobile ? COMPONENT_MOBILE_LOGIN_PAGE_URL : COMPONENT_LOGIN_PAGE_URL),
                this.getWxOpenConfig().getComponentAppId(),
                jsonObject.get("pre_auth_code").getAsString(),
                URIUtil.encodeURIComponent(redirectURI));
        if (StringUtils.isNotEmpty(authType)) {
            preAuthUrlStr = preAuthUrlStr.replace("&auth_type=xxx", "&auth_type=" + authType);
        } else {
            preAuthUrlStr = preAuthUrlStr.replace("&auth_type=xxx", "");
        }
        if (StringUtils.isNotEmpty(bizAppid)) {
            preAuthUrlStr = preAuthUrlStr.replace("&biz_appid=xxx", "&biz_appid=" + bizAppid);
        } else {
            preAuthUrlStr = preAuthUrlStr.replace("&biz_appid=xxx", "");
        }

        return preAuthUrlStr;
    }

    @Override
    public String route(final WxOpenXmlMessage wxMessage) throws WxErrorException {
        if (wxMessage == null) {
            throw new NullPointerException("message is empty");
        }
        if (StringUtils.equalsIgnoreCase(wxMessage.getInfoType(), "component_verify_ticket")) {
            this.getWxOpenConfig().setComponentVerifyTicket(wxMessage.getComponentVerifyTicket());
            return "success";
        }
        //新增、跟新授权
        if (StringUtils.equalsAnyIgnoreCase(wxMessage.getInfoType(), "authorized", "updateauthorized")) {
            WxOpenQueryAuthResult queryAuth = wxOpenService.getWxOpenComponentService().getQueryAuth(wxMessage.getAuthorizationCode());
            if (queryAuth == null || queryAuth.getAuthorizationInfo() == null || queryAuth.getAuthorizationInfo().getAuthorizerAppid() == null) {
                throw new NullPointerException("getQueryAuth");
            }

            return "success";
        }

        return "";
    }

    @Override
    public WxOpenQueryAuthResult getQueryAuth(String authorizationCode) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
        jsonObject.addProperty("authorization_code", authorizationCode);
        String responseContent = post(API_QUERY_AUTH_URL, jsonObject.toString());

        WxOpenQueryAuthResult queryAuth = WxOpenGsonBuilder.create().fromJson(responseContent, WxOpenQueryAuthResult.class);
        if (queryAuth == null || queryAuth.getAuthorizationInfo() == null) {
            return queryAuth;
        }

        WxOpenAuthorizationInfo authorizationInfo = queryAuth.getAuthorizationInfo();
        if (authorizationInfo.getAuthorizerAccessToken() != null) {
            this.getWxOpenConfig().updateAuthorizerAccessToken(authorizationInfo.getAuthorizerAppid(),
                    authorizationInfo.getAuthorizerAccessToken(), authorizationInfo.getExpiresIn());
        }

        if (authorizationInfo.getAuthorizerRefreshToken() != null) {
            this.getWxOpenConfig().setAuthorizerRefreshToken(authorizationInfo.getAuthorizerAppid(), authorizationInfo.getAuthorizerRefreshToken());
        }

        return queryAuth;
    }

    @Override
    public WxOpenAuthorizerInfoResult getAuthorizerInfo(String authorizerAppid) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
        jsonObject.addProperty("authorizer_appid", authorizerAppid);
        String responseContent = post(API_GET_AUTHORIZER_INFO_URL, jsonObject.toString());

        return WxOpenGsonBuilder.create().fromJson(responseContent, WxOpenAuthorizerInfoResult.class);
    }

    @Override
    public WxOpenAuthorizerOptionResult getAuthorizerOption(String authorizerAppid, String optionName) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
        jsonObject.addProperty("authorizer_appid", authorizerAppid);
        jsonObject.addProperty("option_name", optionName);
        String responseContent = post(API_GET_AUTHORIZER_OPTION_URL, jsonObject.toString());

        return WxOpenGsonBuilder.create().fromJson(responseContent, WxOpenAuthorizerOptionResult.class);
    }

    @Override
    public void setAuthorizerOption(String authorizerAppid, String optionName, String optionValue) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
        jsonObject.addProperty("authorizer_appid", authorizerAppid);
        jsonObject.addProperty("option_name", optionName);
        jsonObject.addProperty("option_value", optionValue);
        post(API_SET_AUTHORIZER_OPTION_URL, jsonObject.toString());
    }

    @Override
    public String getAuthorizerAccessToken(String appId, boolean forceRefresh) throws WxErrorException {
        Lock lock = this.getWxOpenConfig().getAuthorizerAccessTokenLock(appId);
        try {
            lock.lock();

            if (this.getWxOpenConfig().isAuthorizerAccessTokenExpired(appId) || forceRefresh) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("component_appid", this.getWxOpenConfig().getComponentAppId());
                jsonObject.addProperty("authorizer_appid", appId);
                jsonObject.addProperty("authorizer_refresh_token", this.getWxOpenConfig().getAuthorizerRefreshToken(appId));
                String responseContent = post(API_AUTHORIZER_TOKEN_URL, jsonObject.toString());

                WxOpenAuthorizerAccessToken wxOpenAuthorizerAccessToken = WxOpenAuthorizerAccessToken.fromJson(responseContent);
                this.getWxOpenConfig().updateAuthorizerAccessToken(appId, wxOpenAuthorizerAccessToken);
            }
        } finally {
            lock.unlock();
        }

        return this.getWxOpenConfig().getAuthorizerAccessToken(appId);
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String appId, String code) throws WxErrorException {
        String url = String.format(OAUTH2_ACCESS_TOKEN_URL, appId, code, this.getWxOpenConfig().getComponentAppId());
        String responseContent = get(url);

        return WxMpOAuth2AccessToken.fromJson(responseContent);
    }

    @Override
    public boolean checkSignature(String appid, String timestamp, String nonce, String signature) {
        return false;
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String appId, String refreshToken) throws WxErrorException {
        String url = String.format(OAUTH2_REFRESH_TOKEN_URL, appId, refreshToken, this.getWxOpenConfig().getComponentAppId());
        String responseContent = get(url);

        return WxMpOAuth2AccessToken.fromJson(responseContent);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String appId, String redirectURI, String scope, String state) {
        return String.format(CONNECT_OAUTH2_AUTHORIZE_URL,
                appId, URIUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state), this.getWxOpenConfig().getComponentAppId());
    }

    @Override
    public WxMaJscode2SessionResult maJscode2Session(String appId, String jsCode) throws WxErrorException {
        String url = String.format(MINIAPP_JSCODE_2_SESSION, appId, jsCode, this.getWxOpenConfig().getComponentAppId());
        String responseContent = get(url);

        return WxMaJscode2SessionResult.fromJson(responseContent);
    }

    @Override
    public List<WxOpenMaCodeTemplate> getTemplateDraftList() throws WxErrorException {
        String responseContent = get(GET_TEMPLATE_DRAFT_LIST_URL, "access_token");
        JsonObject response = JSON_PARSER.parse(StringUtils.defaultString(responseContent, "{}")).getAsJsonObject();
        boolean hasDraftList = response.has("draft_list");
        if (hasDraftList) {
            return WxOpenGsonBuilder.create().fromJson(response.getAsJsonArray("draft_list"),
                    new TypeToken<List<WxOpenMaCodeTemplate>>() {
                    }.getType());
        } else {
            return null;
        }
    }

    @Override
    public List<WxOpenMaCodeTemplate> getTemplateList() throws WxErrorException {
        String responseContent = get(GET_TEMPLATE_LIST_URL, "access_token");
        JsonObject response = JSON_PARSER.parse(StringUtils.defaultString(responseContent, "{}")).getAsJsonObject();
        boolean hasDraftList = response.has("template_list");
        if (hasDraftList) {
            return WxOpenGsonBuilder.create().fromJson(response.getAsJsonArray("template_list"),
                    new TypeToken<List<WxOpenMaCodeTemplate>>() {
                    }.getType());
        } else {
            return null;
        }
    }

    @Override
    public void addToTemplate(long draftId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("draft_id", draftId);
        post(ADD_TO_TEMPLATE_URL, param.toString(), "access_token");
    }

    @Override
    public void deleteTemplate(long templateId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("template_id", templateId);
        post(DELETE_TEMPLATE_URL, param.toString(), "access_token");
    }
}
