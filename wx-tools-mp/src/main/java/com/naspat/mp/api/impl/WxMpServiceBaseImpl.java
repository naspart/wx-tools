package com.naspat.mp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naspat.common.bean.WxJsapiSignature;
import com.naspat.common.enums.TicketType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.session.StandardSessionManager;
import com.naspat.common.session.WxSessionManager;
import com.naspat.common.util.DataUtils;
import com.naspat.common.util.RandomUtils;
import com.naspat.common.util.crypto.SHA1;
import com.naspat.common.util.http.*;
import com.naspat.mp.api.*;
import com.naspat.mp.bean.WxMpCurrentAutoReplyInfo;
import com.naspat.mp.bean.WxMpOAuth2AccessToken;
import com.naspat.mp.bean.WxMpSemanticQuery;
import com.naspat.mp.bean.WxMpSemanticQueryResult;
import com.naspat.mp.bean.user.WxMpUser;
import com.naspat.mp.config.WxMpConfig;
import com.naspat.mp.enums.WxMpApiUrl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class WxMpServiceBaseImpl implements WxMpService, RequestHttp {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final JsonParser JSON_PARSER = new JsonParser();

    private WxSessionManager sessionManager = new StandardSessionManager();
    private WxMpConfig wxMpConfig;

    private WxMpKefuService kefuService = new WxMpKefuServiceImpl(this);
    private WxMpMaterialService materialService = new WxMpMaterialServiceImpl(this);
    private WxMpMenuService menuService = new WxMpMenuServiceImpl(this);
    private WxMpUserService userService = new WxMpUserServiceImpl(this);
    private WxMpUserTagService tagService = new WxMpUserTagServiceImpl(this);
    private WxMpQrcodeService qrCodeService = new WxMpQrcodeServiceImpl(this);
    private WxMpCardService cardService = new WxMpCardServiceImpl(this);
    private WxMpStoreService storeService = new WxMpStoreServiceImpl(this);
    private WxMpDataCubeService dataCubeService = new WxMpDataCubeServiceImpl(this);
    private WxMpUserBlacklistService blackListService = new WxMpUserBlacklistServiceImpl(this);
    private WxMpTemplateMsgService templateMsgService = new WxMpTemplateMsgServiceImpl(this);
    private WxMpSubscribeMsgService subscribeMsgService = new WxMpSubscribeMsgServiceImpl(this);
    private WxMpDeviceService deviceService = new WxMpDeviceServiceImpl(this);
    private WxMpShakeService shakeService = new WxMpShakeServiceImpl(this);
    private WxMpMemberCardService memberCardService = new WxMpMemberCardServiceImpl(this);
    private WxMpMassMessageService massMessageService = new WxMpMassMessageServiceImpl(this);
    private WxMpAiOpenService aiOpenService = new WxMpAiOpenServiceImpl(this);
    private WxMpWifiService wifiService = new WxMpWifiServiceImpl(this);

    private int retrySleepMillis = 1000;
    private int maxRetryTimes = 5;

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(this.getWxMpConfig().getToken(), timestamp, nonce)
                    .equals(signature);
        } catch (Exception e) {
            this.logger.error("Checking signature failed, and the reason is :" + e.getMessage());
            return false;
        }
    }

    @Override
    public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String randomStr = RandomUtils.getRandomStr();
        String jsApiTicket = this.getWxMpConfig().getTicket(TicketType.JSAPI);
        String signature = SHA1.genWithAmple("jsapi_ticket=" + jsApiTicket, "noncestr=" + randomStr, "timestamp=" + timestamp, "url=" + url);

        WxJsapiSignature jsapiSignature = new WxJsapiSignature();
        jsapiSignature.setAppId(this.getWxMpConfig().getAppId());
        jsapiSignature.setTimestamp(timestamp);
        jsapiSignature.setNonceStr(randomStr);
        jsapiSignature.setUrl(url);
        jsapiSignature.setSignature(signature);

        return jsapiSignature;
    }

    @Override
    public String shortUrl(String longUrl) throws WxErrorException {
        JsonObject o = new JsonObject();
        o.addProperty("action", "long2short");
        o.addProperty("long_url", longUrl);
        String responseContent = this.post(WxMpService.SHORTURL_API_URL, o.toString());
        JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);

        return tmpJsonElement.getAsJsonObject().get("short_url").getAsString();
    }

    @Override
    public WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException {
        String responseContent = this.post(WxMpService.SEMANTIC_SEMPROXY_SEARCH_URL, semanticQuery.toJson());
        return WxMpSemanticQueryResult.fromJson(responseContent);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        return String.format(WxMpService.CONNECT_OAUTH2_AUTHORIZE_URL,
                this.getWxMpConfig().getAppId(), URIUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state));
    }

    @Override
    public String buildQrConnectUrl(String redirectURI, String scope, String state) {
        return String.format(WxMpService.QRCONNECT_URL,
                this.getWxMpConfig().getAppId(), URIUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state));
    }

    private WxMpOAuth2AccessToken getOAuth2AccessToken(String url) throws WxErrorException {
        try {
            RequestExecutor<String, String> executor = SimpleGetRequestExecutor.create(this);
            String responseText = executor.execute(url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
        String url = String.format(WxMpService.OAUTH2_ACCESS_TOKEN_URL, this.getWxMpConfig().getAppId(), this.getWxMpConfig().getSecret(), code);

        return this.getOAuth2AccessToken(url);
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException {
        String url = String.format(WxMpService.OAUTH2_REFRESH_TOKEN_URL, this.getWxMpConfig().getAppId(), refreshToken);

        return this.getOAuth2AccessToken(url);
    }

    @Override
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken token, String lang) throws WxErrorException {
        if (lang == null) {
            lang = "zh_CN";
        }

        String url = String.format(WxMpService.OAUTH2_USERINFO_URL, token.getAccessToken(), token.getOpenId(), lang);

        try {
            RequestExecutor<String, String> executor = SimpleGetRequestExecutor.create(this);
            String responseText = executor.execute(url, null);
            return WxMpUser.fromJson(responseText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean oauth2validateAccessToken(WxMpOAuth2AccessToken token) {
        String url = String.format(WxMpService.OAUTH2_VALIDATE_TOKEN_URL, token.getAccessToken(), token.getOpenId());

        try {
            SimpleGetRequestExecutor.create(this).execute(url, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WxErrorException e) {
            return false;
        }

        return true;
    }

    @Override
    public String[] getCallbackIP() throws WxErrorException {
        String responseContent = this.get(WxMpService.GET_CALLBACK_IP_URL, null);
        JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
        JsonArray ipList = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();

        String[] ipArray = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            ipArray[i] = ipList.get(i).getAsString();
        }

        return ipArray;
    }

    @Override
    public WxMpCurrentAutoReplyInfo getCurrentAutoReplyInfo() throws WxErrorException {
        return WxMpCurrentAutoReplyInfo.fromJson(this.get(GET_CURRENT_AUTOREPLY_INFO_URL, null));
    }

    @Override
    public void clearQuota(String appid) throws WxErrorException {
        JsonObject o = new JsonObject();
        o.addProperty("appid", appid);
        this.post(CLEAR_QUOTA_URL, o.toString());
    }

    @Override
    public String get(String url, String queryParam) throws WxErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws WxErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }

    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, WxMpApiUrl url, E data) throws WxErrorException {
        return this.execute(executor, url.getUrl(this.getWxMpConfig()), data);
    }

    /**
     * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求.
     */
    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, uri, data);
            } catch (WxErrorException e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    this.logger.warn("重试达到最大次数【{}】", maxRetryTimes);
                    //最后一次重试失败后，直接抛出异常，不再等待
                    throw new RuntimeException("微信服务端异常，超出重试次数");
                }

                WxError error = e.getError();
                // -1 系统繁忙, 1000ms后重试
                if (error.getErrorCode() == -1) {
                    int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                    try {
                        this.logger.warn("微信系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);

        this.logger.warn("重试达到最大次数【{}】", this.maxRetryTimes);
        throw new RuntimeException("微信服务端异常，超出重试次数");
    }

    private <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        E dataForLog = DataUtils.handleDataWithSecret(data);

        if (uri.contains("access_token=")) {
            throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
        }

        String accessToken = this.getWxMpConfig().getAccessToken();

        String uriWithAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;

        try {
            T result = executor.execute(uriWithAccessToken, data);
            this.logger.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uriWithAccessToken, dataForLog, result);

            return result;
        } catch (WxErrorException e) {
            WxError error = e.getError();
            if (error.getErrorCode() != 0) {
                this.logger.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uriWithAccessToken, dataForLog, error);
                throw new WxErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            this.logger.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uriWithAccessToken, dataForLog, e.getMessage());
            throw new WxErrorException(WxError.builder().errorMsg(e.getMessage()).build(), e);
        }
    }

    @Override
    public WxMpConfig getWxMpConfig() {
        return this.wxMpConfig;
    }

    @Override
    public void setWxMpConfig(WxMpConfig wxConfigProvider) {
        this.wxMpConfig = wxConfigProvider;
        this.initHttp();
    }

    @Override
    public void setRetrySleepMillis(int retrySleepMillis) {
        this.retrySleepMillis = retrySleepMillis;
    }

    @Override
    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public WxMpKefuService getKefuService() {
        return this.kefuService;
    }

    @Override
    public WxMpMaterialService getMaterialService() {
        return this.materialService;
    }

    @Override
    public WxMpMenuService getMenuService() {
        return this.menuService;
    }

    @Override
    public WxMpUserService getUserService() {
        return this.userService;
    }

    @Override
    public WxMpUserTagService getUserTagService() {
        return this.tagService;
    }

    @Override
    public WxMpQrcodeService getQrcodeService() {
        return this.qrCodeService;
    }

    @Override
    public WxMpCardService getCardService() {
        return this.cardService;
    }

    @Override
    public WxMpDataCubeService getDataCubeService() {
        return this.dataCubeService;
    }

    @Override
    public WxMpUserBlacklistService getBlackListService() {
        return this.blackListService;
    }

    @Override
    public WxMpStoreService getStoreService() {
        return this.storeService;
    }

    @Override
    public WxMpTemplateMsgService getTemplateMsgService() {
        return this.templateMsgService;
    }

    @Override
    public WxMpSubscribeMsgService getSubscribeMsgService() {
        return this.subscribeMsgService;
    }

    @Override
    public WxMpDeviceService getDeviceService() {
        return this.deviceService;
    }

    @Override
    public WxMpShakeService getShakeService() {
        return this.shakeService;
    }

    @Override
    public WxMpMemberCardService getMemberCardService() {
        return this.memberCardService;
    }

    @Override
    public RequestHttp getRequestHttp() {
        return this;
    }

    @Override
    public WxMpMassMessageService getMassMessageService() {
        return this.massMessageService;
    }

    @Override
    public void setKefuService(WxMpKefuService kefuService) {
        this.kefuService = kefuService;
    }

    @Override
    public void setMaterialService(WxMpMaterialService materialService) {
        this.materialService = materialService;
    }

    @Override
    public void setMenuService(WxMpMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void setUserService(WxMpUserService userService) {
        this.userService = userService;
    }

    @Override
    public void setTagService(WxMpUserTagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void setQrCodeService(WxMpQrcodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Override
    public void setCardService(WxMpCardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void setStoreService(WxMpStoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public void setDataCubeService(WxMpDataCubeService dataCubeService) {
        this.dataCubeService = dataCubeService;
    }

    @Override
    public void setBlackListService(WxMpUserBlacklistService blackListService) {
        this.blackListService = blackListService;
    }

    @Override
    public void setTemplateMsgService(WxMpTemplateMsgService templateMsgService) {
        this.templateMsgService = templateMsgService;
    }

    @Override
    public void setDeviceService(WxMpDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void setShakeService(WxMpShakeService shakeService) {
        this.shakeService = shakeService;
    }

    @Override
    public void setMemberCardService(WxMpMemberCardService memberCardService) {
        this.memberCardService = memberCardService;
    }

    @Override
    public void setMassMessageService(WxMpMassMessageService massMessageService) {
        this.massMessageService = massMessageService;
    }

    @Override
    public WxMpAiOpenService getAiOpenService() {
        return this.aiOpenService;
    }

    @Override
    public void setAiOpenService(WxMpAiOpenService aiOpenService) {
        this.aiOpenService = aiOpenService;
    }

    @Override
    public WxMpWifiService getWifiService() {
        return this.wifiService;
    }
}
