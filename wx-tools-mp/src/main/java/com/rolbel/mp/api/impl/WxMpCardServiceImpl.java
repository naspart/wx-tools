package com.rolbel.mp.api.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.bean.WxCardApiSignature;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.RandomUtils;
import com.rolbel.common.util.crypto.SHA1;
import com.rolbel.mp.api.WxMpCardService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.card.WxMpCardLandingPageCreateRequest;
import com.rolbel.mp.bean.card.WxMpCardLandingPageCreateResult;
import com.rolbel.mp.bean.card.WxMpCardQrcodeCreateResult;
import com.rolbel.mp.bean.card.WxMpCardResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class WxMpCardServiceImpl implements WxMpCardService {
    private final Logger log = LoggerFactory.getLogger(WxMpCardServiceImpl.class);

    private WxMpService wxMpService;
    private static final Gson GSON = WxMpGsonBuilder.create();

    WxMpCardServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpService getWxMpService() {
        return this.wxMpService;
    }

    /**
     * 获得卡券api_ticket，不强制刷新卡券api_ticket.
     *
     * @return 卡券api_ticket
     */
    @Override
    public String getCardApiTicket() throws WxErrorException {
        return this.getWxMpService().getWxMpConfig().getCardApiTicket();
    }

    /**
     * <pre>
     * 创建调用卡券api时所需要的签名
     *
     * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD
     * .954-.E5.8D.A1.E5.88.B8.E6.89.A9.E5.B1.95.E5.AD.97.E6.AE.B5.E5.8F.8A.E7.AD.BE.E5.90.8D.E7.94
     * .9F.E6.88.90.E7.AE.97.E6.B3.95
     * </pre>
     *
     * @param optionalSignParam 参与签名的参数数组。
     *                          可以为下列字段：app_id, card_id, card_type, code, openid, location_id
     *                          </br>注意：当做wx.chooseCard调用时，必须传入app_id参与签名，否则会造成签名失败导致拉取卡券列表为空
     * @return 卡券Api签名对象
     */
    @Override
    public WxCardApiSignature createCardApiSignature(String... optionalSignParam) throws
            WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String nonceStr = RandomUtils.getRandomStr();
        String cardApiTicket = getCardApiTicket();

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
     * 卡券Code解码
     *
     * @param encryptCode 加密Code，通过JSSDK的chooseCard接口获得
     * @return 解密后的Code
     */
    @Override
    public String decryptCardCode(String encryptCode) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("encrypt_code", encryptCode);
        String responseContent = this.wxMpService.post(CARD_CODE_DECRYPT, param.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = tmpJsonObject.getAsJsonPrimitive("code");
        return jsonPrimitive.getAsString();
    }

    /**
     * 卡券Code查询.
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
        String responseContent = this.wxMpService.post(CARD_CODE_GET, param.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return WxMpGsonBuilder.create().fromJson(tmpJsonElement,
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

        if (cardId != null && !"".equals(cardId)) {
            param.addProperty("card_id", cardId);
        }

        return this.wxMpService.post(CARD_CODE_CONSUME, param.toString());
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
    public void markCardCode(String code, String cardId, String openId, boolean isMark) throws
            WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("code", code);
        param.addProperty("card_id", cardId);
        param.addProperty("openid", openId);
        param.addProperty("is_mark", isMark);
        String responseContent = this.getWxMpService().post(CARD_CODE_MARK, param.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        WxMpCardResult cardResult = WxMpGsonBuilder.create().fromJson(tmpJsonElement,
                new TypeToken<WxMpCardResult>() {
                }.getType());
        if (!"0".equals(cardResult.getErrorCode())) {
            this.log.warn("朋友的券mark失败：{}", cardResult.getErrorMsg());
        }
    }

    @Override
    public String getCardDetail(String cardId) throws WxErrorException {
        JsonObject param = new JsonObject();
        param.addProperty("card_id", cardId);
        String responseContent = this.wxMpService.post(CARD_GET, param.toString());

        // 判断返回值
        JsonObject json = (new JsonParser()).parse(responseContent).getAsJsonObject();
        String errcode = json.get("errcode").getAsString();
        if (!"0".equals(errcode)) {
            String errmsg = json.get("errmsg").getAsString();
            throw new WxErrorException(WxError.builder()
                    .errorCode(Integer.valueOf(errcode)).errorMsg(errmsg)
                    .build());
        }

        return responseContent;
    }

    /**
     * 添加测试白名单.
     *
     * @param openid 用户的openid
     */
    @Override
    public String addTestWhiteList(String openid) throws WxErrorException {
        JsonArray array = new JsonArray();
        array.add(openid);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("openid", array);

        return this.wxMpService.post(CARD_TEST_WHITELIST, GSON.toJson(jsonObject));
    }

    /**
     * 创建卡券二维码.
     */
    @Override
    public WxMpCardQrcodeCreateResult createQrcodeCard(String cardId, String outerStr) throws WxErrorException {
        return createQrcodeCard(cardId, outerStr, 0);
    }

    /**
     * 创建卡券二维码.
     *
     * @param cardId    卡券编号
     * @param outerStr  二维码标识
     * @param expiresIn 失效时间，单位秒，不填默认365天
     */
    @Override
    public WxMpCardQrcodeCreateResult createQrcodeCard(String cardId, String outerStr, int expiresIn) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action_name", "QR_CARD");
        if (expiresIn > 0) {
            jsonObject.addProperty("expire_seconds", expiresIn);
        }
        JsonObject actionInfoJson = new JsonObject();
        JsonObject cardJson = new JsonObject();
        cardJson.addProperty("card_id", cardId);
        cardJson.addProperty("outer_str", outerStr);
        actionInfoJson.add("card", cardJson);
        jsonObject.add("action_info", actionInfoJson);

        return WxMpCardQrcodeCreateResult.fromJson(this.wxMpService.post(CARD_QRCODE_CREATE, GSON.toJson(jsonObject)));
    }

    /**
     * 创建卡券货架接口.
     */
    @Override
    public WxMpCardLandingPageCreateResult createLandingPage(WxMpCardLandingPageCreateRequest request) throws WxErrorException {
        String response = this.wxMpService.post(CARD_LANDING_PAGE_CREATE, GSON.toJson(request));
        return WxMpCardLandingPageCreateResult.fromJson(response);
    }

    /**
     * 将用户的卡券设置为失效状态.
     * 详见:https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025272&anchor=9
     *
     * @param cardId 卡券编号
     * @param code   用户会员卡号
     * @param reason 设置为失效的原因
     */
    @Override
    public String unavailableCardCode(String cardId, String code, String reason) throws WxErrorException {
        if (StringUtils.isAnyBlank(cardId, code, reason)) {
            throw new WxErrorException(WxError.builder().errorCode(41012).errorMsg("参数不完整").build());
        }
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("card_id", cardId);
        jsonRequest.addProperty("code", code);
        jsonRequest.addProperty("reason", reason);
        return this.wxMpService.post(CARD_CODE_UNAVAILABLE, GSON.toJson(jsonRequest));
    }
}
