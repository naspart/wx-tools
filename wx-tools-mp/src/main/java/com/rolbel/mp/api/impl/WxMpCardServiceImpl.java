package com.rolbel.mp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.bean.WxCardApiSignature;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.util.RandomUtil;
import com.rolbel.common.util.crypto.SHA1;
import com.rolbel.common.util.http.SimpleGetRequestExecutor;
import com.rolbel.mp.api.WxMpCardService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.card.base.WxMpCardCreateRequest;
import com.rolbel.mp.bean.card.request.WxMpCard;
import com.rolbel.mp.bean.card.request.WxMpCreateLandingPage;
import com.rolbel.mp.bean.card.request.WxMpImportCardCode;
import com.rolbel.mp.bean.card.result.WxMpCreateCardResult;
import com.rolbel.mp.bean.card.result.WxMpCheckCodeResult;
import com.rolbel.mp.bean.card.result.WxMpCreateLandingPageResult;
import com.rolbel.mp.bean.card.result.WxMpGetUserCardResult;
import com.rolbel.mp.bean.result.WxMpCardResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class WxMpCardServiceImpl implements WxMpCardService {
    private WxMpService wxMpService;

    public WxMpCardServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpService getWxMpService() {
        return this.wxMpService;
    }

    /**
     * <pre>
     *     创建卡券
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056
     * </pre>
     *
     * @param wxMpGrouponCard 卡券详情
     * @return 卡券card_id
     * @throws WxErrorException
     */
    @Override
    public WxMpCreateCardResult createCard(WxMpCardCreateRequest wxMpGrouponCard) throws WxErrorException {
        String responseContent = this.wxMpService.post(WxMpCardService.CARD_CREATE_URL, wxMpGrouponCard.toJson());

        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpCreateCardResult>() {
                }.getType());
    }

    /**
     * <pre>
     *     设置买单
     *
     *     创建卡券之后，开发者可以通过设置微信买单接口设置该card_id支持微信买单功能。值得开发者注意的是，设置买单的card_id必须已经配置了门店，否则会报错。
     *
     *     注意事项：
     *     1.设置快速买单的卡券须支持至少一家有核销员门店，否则无法设置成功；
     *     2.若该卡券设置了center_url（居中使用跳转链接）,须先将该设置更新为空后再设置自快速买单方可生效。
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056
     * </pre>
     *
     * @param cardId 卡券card_id
     * @param isOpen 是否开启买单功能
     * @throws WxErrorException
     */
    @Override
    public void setPayCell(String cardId, boolean isOpen) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);
        param.addProperty("is_open", isOpen);

        this.wxMpService.post(CARD_PAYCELL_SET_URL, param.toString());
    }

    /**
     * <pre>
     *     设置自助核销
     *
     *     创建卡券之后，开发者可以通过设置微信买单接口设置该card_id支持自助核销功能。值得开发者注意的是，设置自助核销的card_id必须已经配置了门店，否则会报错。
     *
     *     注意事项：
     *     1.设置自助核销的卡券须支持至少一家门店，否则无法设置成功；
     *     2.若该卡券设置了center_url（居中使用跳转链接）,须先将该设置更新为空后再设置自助核销功能方可生效。
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056
     * </pre>
     *
     * @param cardId 卡券card_id
     * @param isOpen 是否开启买单功能
     * @throws WxErrorException
     */
    @Override
    public void setSelfConsumeCell(String cardId, boolean isOpen, boolean needVerifyCode, boolean needRemarkAmount) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);
        param.addProperty("is_open", isOpen);
        param.addProperty("need_verify_code", needVerifyCode);
        param.addProperty("need_remark_amount", needRemarkAmount);

        this.wxMpService.post(SELF_CONSUME_CELL_SET_URL, param.toString());
    }

    public void createQrcode() {

    }

    /**
     * <pre>
     *     创建货架接口
     *
     *     开发者需调用该接口创建货架链接，用于卡券投放。创建货架时需填写投放路径的场景字段。
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062
     * </pre>
     */
    @Override
    public WxMpCreateLandingPageResult createCardLandingPage(WxMpCreateLandingPage request) throws WxErrorException {
        String responseContent = this.wxMpService.post(CREATE_LANDING_PAGE_URL, request.toJson());

        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpCreateLandingPageResult>() {
                }.getType());
    }

    /**
     * <pre>
     *     导入code接口
     *
     *     在自定义code卡券成功创建并且通过审核后，必须将自定义code按照与发券方的约定数量调用导入code接口导入微信后台。
     *
     *     接口说明：开发者可调用该接口将自定义code导入微信卡券后台，由微信侧代理存储并下发code。
     *
     *     注：
     *     1）单次调用接口传入code的数量上限为100个。
     *     2）每一个 code 均不能为空串。
     *     3）导入结束后系统会自动判断提供方设置库存与实际导入code的量是否一致。
     *     4）导入失败支持重复导入，提示成功为止。
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062
     * </pre>
     */
    @Override
    public void importCardCode(WxMpImportCardCode request) throws WxErrorException {
        this.wxMpService.post(IMPORT_CARD_CODE_URL, request.toJson());
    }

    /**
     * <pre>
     *     查询导入code数目接口
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062
     * </pre>
     *
     * @param cardId 卡券ID
     * @return 已经成功存入的code数目
     * @throws WxErrorException
     */
    @Override
    public Integer getImportCardCodeCount(String cardId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);

        String responseContent = this.wxMpService.post(GET_IMPORT_CARD_CODE_COUNT_URL, param.toString());

        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = tmpJsonObject.getAsJsonPrimitive("count");

        return jsonPrimitive.getAsInt();
    }

    /**
     * <pre>
     *     核查code接口
     *
     *     为了避免出现导入差错，强烈建议开发者在查询完code数目的时候核查code接口校验code导入微信后台的情况。
     * </pre>
     *
     * @param request
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpCheckCodeResult checkCode(WxMpImportCardCode request) throws WxErrorException {
        String responseContent = this.wxMpService.post(CHECK_CODE_URL, request.toJson());

        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpCheckCodeResult>() {
                }.getType());
    }

    /**
     * <pre>
     *     图文消息群发卡券
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062
     * </pre>
     *
     * @param cardId 卡券ID
     * @return 返回一段html代码，可以直接嵌入到图文消息的正文里。即可以把这段代码嵌入到 上传图文消息素材接口 中的content字段里
     * @throws WxErrorException
     */
    @Override
    public String wxMpNewsDistributeCard(String cardId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);

        String responseContent = this.wxMpService.post(MP_NEWS_DISTRIBUTE_CARD_URL, param.toString());

        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = tmpJsonObject.getAsJsonPrimitive("content");

        return jsonPrimitive.getAsString();
    }

    /**
     * 获得卡券api_ticket，不强制刷新卡券api_ticket
     *
     * @return 卡券api_ticket
     * @see #getCardApiTicket(boolean)
     */
    @Override
    public String getCardApiTicket() throws WxErrorException {
        return getCardApiTicket(false);
    }

    /**
     * <pre>
     * 获得卡券api_ticket
     * 获得时会检查卡券apiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
     *
     * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     * </pre>
     *
     * @param forceRefresh 强制刷新
     * @return 卡券api_ticket
     */
    @Override
    public String getCardApiTicket(boolean forceRefresh) throws WxErrorException {
        Lock lock = getWxMpService().getWxMpConfigStorage().getCardApiTicketLock();
        try {
            lock.lock();

            if (forceRefresh) {
                this.getWxMpService().getWxMpConfigStorage().expireCardApiTicket();
            }

            if (this.getWxMpService().getWxMpConfigStorage().isCardApiTicketExpired()) {
                String responseContent = this.wxMpService.execute(SimpleGetRequestExecutor.create(this.getWxMpService().getRequestHttp()), CARD_GET_TICKET_URL, null);
                JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
                JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
                String cardApiTicket = tmpJsonObject.get("ticket").getAsString();
                int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
                this.getWxMpService().getWxMpConfigStorage().updateCardApiTicket(cardApiTicket, expiresInSeconds);
            }
        } finally {
            lock.unlock();
        }

        return this.getWxMpService().getWxMpConfigStorage().getCardApiTicket();
    }

    /**
     * <pre>
     *     创建调用卡券api时所需要的签名
     *
     *     详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     * </pre>
     *
     * @param optionalSignParam 参与签名的参数数组。
     *                          可以为下列字段：app_id, card_id, card_type, code, openid, location_id
     *                          </br>注意：当做wx.chooseCard调用时，必须传入app_id参与签名，否则会造成签名失败导致拉取卡券列表为空
     * @return 卡券Api签名对象
     */
    @Override
    public WxCardApiSignature createCardApiSignature(String... optionalSignParam) throws WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomUtil.getRandomStr();
        String cardApiTicket = getCardApiTicket(false);

        String[] signParam = Arrays.copyOf(optionalSignParam, optionalSignParam.length + 3);
        signParam[optionalSignParam.length] = String.valueOf(timestamp);
        signParam[optionalSignParam.length + 1] = nonceStr;
        signParam[optionalSignParam.length + 2] = cardApiTicket;

        String signature = SHA1.gen(signParam);

        WxCardApiSignature cardApiSignature = new WxCardApiSignature();
        cardApiSignature.setTimestamp(timestamp);
        cardApiSignature.setNonceStr(nonceStr);
        cardApiSignature.setSignature(signature);

        return cardApiSignature;
    }

    /**
     * 卡券Code查询
     *
     * @param cardId       卡券ID代表一类卡券
     * @param code         单张卡券的唯一标准
     * @param checkConsume 是否校验code核销状态，填入true和false时的code异常状态返回数据不同
     * @return WxMpCardResult对象
     */
    @Override
    public WxMpCardResult queryCardCode(String cardId, String code, boolean checkConsume) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);
        param.addProperty("code", code);
        param.addProperty("check_consume", checkConsume);

        String responseContent = this.wxMpService.post(CARD_CODE_GET_URL, param.toString());

        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpCardResult>() {
                }.getType());
    }

    /**
     * 卡券Code核销。核销失败会抛出异常
     *
     * @param code 单张卡券的唯一标准
     * @return 调用返回的JSON字符串。
     * <br>可用 com.google.gson.JsonParser#parse 等方法直接取JSON串中的errcode等信息。
     */
    @Override
    public String consumeCardCode(String code) throws WxErrorException {
        return consumeCardCode(code, null);
    }

    /**
     * 卡券Code核销。核销失败会抛出异常
     *
     * @param code   单张卡券的唯一标准
     * @param cardId 当自定义Code卡券时需要传入card_id
     * @return 调用返回的JSON字符串。
     * <br>可用 com.google.gson.JsonParser#parse 等方法直接取JSON串中的errcode等信息。
     */
    @Override
    public String consumeCardCode(String code, String cardId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("code", code);

        if (StringUtils.isNotBlank(cardId)) {
            param.addProperty("card_id", cardId);
        }

        return this.wxMpService.post(CARD_CODE_CONSUME_URL, param.toString());
    }

    /**
     * 卡券Code解码
     *
     * @param encryptCode 加密Code，通过JSSDK的chooseCard接口获得
     * @return 解密后的Code
     */
    @Override
    public String decryptCardCode(String encryptCode) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("encrypt_code", encryptCode);

        String responseContent = this.wxMpService.post(CARD_CODE_DECRYPT_URL, param.toString());

        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = tmpJsonObject.getAsJsonPrimitive("code");

        return jsonPrimitive.getAsString();
    }

    /**
     * 卡券Mark接口。
     * 开发者在帮助消费者核销卡券之前，必须帮助先将此code（卡券串码）与一个openid绑定（即mark住），
     * 才能进一步调用核销接口，否则报错。
     *
     * @param code   卡券的code码
     * @param cardId 卡券的ID
     * @param openId 用券用户的openid
     * @param isMark 是否要mark（占用）这个code，填写true或者false，表示占用或解除占用
     */
    @Override
    public void markCardCode(String code, String cardId, String openId, boolean isMark) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("code", code);
        param.addProperty("card_id", cardId);
        param.addProperty("openid", openId);
        param.addProperty("is_mark", isMark);

        this.getWxMpService().post(CARD_CODE_MARK_URL, param.toString());
    }

    /**
     * <pre>
     *     查看卡券详情
     * </pre>
     *
     * @param cardId 卡券的ID
     * @return
     * @throws WxErrorException
     */
    @Override
    public String getCardDetail(String cardId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);

        return this.wxMpService.post(GET_CARD_DETAIL_URL, param.toString());
    }

    /**
     * <pre>
     *     获取用户已领取卡券接口
     *
     *     用于获取用户卡包里的，属于该appid下所有可用卡券，包括正常状态和异常状态
     * </pre>
     *
     * @param openId 需要查询的用户openid
     * @param cardId 卡券ID。不填写时默认查询当前appid下的卡券。
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpGetUserCardResult getUserCardList(String openId, String cardId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("openid", openId);

        if (StringUtils.isNotBlank(cardId)) {
            param.addProperty("card_id", cardId);
        }

        String responseContent = this.getWxMpService().post(GET_USER_CARD_LIST_URL, param.toString());

        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpGetUserCardResult>() {
                }.getType());
    }
}
