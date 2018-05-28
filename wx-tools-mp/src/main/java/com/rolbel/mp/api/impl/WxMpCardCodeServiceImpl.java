package com.rolbel.mp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.api.WxMpCardCodeService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.card.request.WxMpImportCardCode;
import com.rolbel.mp.bean.card.result.WxMpCheckCodeResult;
import com.rolbel.mp.bean.result.WxMpCardResult;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import org.apache.commons.lang3.StringUtils;

public class WxMpCardCodeServiceImpl implements WxMpCardCodeService {
    private WxMpService wxMpService;

    public WxMpCardCodeServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpService getWxMpService() {
        return this.wxMpService;
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
        this.wxMpService.post(CARD_CODE_DEPOSIT_URL, request.toJson());
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

        String responseContent = this.wxMpService.post(CARD_CODE_GET_DEPOSIT_COUNT_URL, param.toString());

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
        String responseContent = this.wxMpService.post(CARD_CODE_CHECK_URL, request.toJson());

        return WxMpGsonBuilder.INSTANCE.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpCheckCodeResult>() {
                }.getType());
    }

    /**
     * 朋友的券-Mark(占用)Code接口
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
}
