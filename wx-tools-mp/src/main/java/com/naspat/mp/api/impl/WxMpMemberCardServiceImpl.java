package com.naspat.mp.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.mp.api.WxMpMemberCardService;
import com.naspat.mp.api.WxMpService;
import com.naspat.mp.bean.card.*;
import com.naspat.mp.bean.card.enums.BusinessServiceType;
import com.naspat.mp.bean.card.enums.CardColor;
import com.naspat.mp.bean.card.enums.DateInfoType;
import com.naspat.mp.bean.member_card.*;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡相关接口的实现类
 */
@Slf4j
public class WxMpMemberCardServiceImpl implements WxMpMemberCardService {
    private WxMpService wxMpService;

    private static final Gson GSON = WxMpGsonBuilder.create();

    WxMpMemberCardServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    /**
     * 得到WxMpService
     */
    @Override
    public WxMpService getWxMpService() {
        return this.wxMpService;
    }

    @Override
    public WxMpCardCreateResult createMemberCard(String createJson) throws WxErrorException {
        WxMpMemberCardCreateMessage createMessage = WxGsonBuilder.create()
                .fromJson(createJson, WxMpMemberCardCreateMessage.class);
        return createMemberCard(createMessage);
    }

    @Override
    public WxMpCardCreateResult createMemberCard(WxMpMemberCardCreateMessage createMessageMessage)
            throws WxErrorException {
        //校验请求对象合法性
        WxMpCardCreateResult validResult = validCheck(createMessageMessage);
        if (!validResult.isSuccess()) {
            return validResult;
        }

        String response = this.wxMpService.post(MEMBER_CARD_CREAET, GSON.toJson(createMessageMessage));
        return WxMpCardCreateResult.fromJson(response);
    }

    private WxMpCardCreateResult validCheck(WxMpMemberCardCreateMessage createMessageMessage) {
        if (createMessageMessage == null) {
            return WxMpCardCreateResult.failure("对象不能为空");
        }
        MemberCardCreateRequest cardCreateRequest = createMessageMessage.getCardCreateRequest();
        if (cardCreateRequest == null) {
            return WxMpCardCreateResult.failure("会员卡对象不能为空");
        }
        String cardType = cardCreateRequest.getCardType();
        if (!StringUtils.equals(cardType, "MEMBER_CARD")) {
            return WxMpCardCreateResult.failure("卡券类型必须等于MEMBER_CARD");
        }
        MemberCard memberCard = cardCreateRequest.getMemberCard();

        if (StringUtils.isEmpty(memberCard.getPrerogative())) {
            return WxMpCardCreateResult.failure("会员卡特权说明不能为空:prerogative");
        }
        //卡片激活规则
        if (!memberCard.isAutoActivate() && !memberCard.isWxActivate()
                && StringUtils.isEmpty(memberCard.getActivateUrl())) {
            return WxMpCardCreateResult.failure("会员卡激活方式为接口激活，activate_url不能为空");
        }

        BaseInfo baseInfo = memberCard.getBaseInfo();
        if (baseInfo == null) {
            return WxMpCardCreateResult.failure("会员卡基本信息对象base_info不能为空");
        }

        if (StringUtils.isBlank(baseInfo.getLogoUrl())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的商户logo:logo_url不能为空");
        }

        if (StringUtils.isBlank(baseInfo.getCodeType())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的条码类型:code_type不能为空");
        }

        if (StringUtils.isBlank(baseInfo.getBrandName())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的商户名字:brand_name不能为空");
        }

        if (StringUtils.length(baseInfo.getBrandName()) > 12) {
            return WxMpCardCreateResult.failure("会员卡基本信息的商户名字:brand_name长度不能大于12个汉字");
        }

        if (StringUtils.isBlank(baseInfo.getTitle())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的卡券名称:title不能为空");
        }

        if (StringUtils.length(baseInfo.getTitle()) > 9) {
            return WxMpCardCreateResult.failure("会员卡基本信息的卡券名称:title长度不能大于9个汉字");
        }

        if (StringUtils.isBlank(baseInfo.getColor())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的卡颜色:color不能为空");
        }

        CardColor cardColor = null;
        try {
            cardColor = CardColor.valueOf(baseInfo.getColor());
        } catch (IllegalArgumentException ex) {

        }
        if (cardColor == null) {
            return WxMpCardCreateResult.failure("会员卡基本信息的卡颜色:" + baseInfo.getColor() + "不支持");
        }

        if (StringUtils.isBlank(baseInfo.getNotice())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的使用提醒:notice不能为空");
        }

        if (StringUtils.isBlank(baseInfo.getDescription())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的使用说明:description不能为空");
        }

        if (baseInfo.getSku() == null) {
            return WxMpCardCreateResult.failure("会员卡基本信息的商品信息:sku不能为空");
        }

        DateInfo dateInfo = baseInfo.getDateInfo();
        if (dateInfo == null) {
            return WxMpCardCreateResult.failure("会员卡基本信息的使用日期:date_info不能为空");
        }

        DateInfoType dateInfoType = null;
        try {
            dateInfoType = DateInfoType.valueOf(dateInfo.getType());
        } catch (IllegalArgumentException ex) {

        }

        if (dateInfoType == null) {
            return WxMpCardCreateResult.failure("会员卡基本信息的使用日期类型:" + dateInfo.getType() + "不合法");
        }

        //固定时长
        if (dateInfoType == DateInfoType.DATE_TYPE_FIX_TERM
                && (dateInfo.getFixedTerm() == null || dateInfo.getFixedBeginTerm() == null)) {
            return WxMpCardCreateResult.failure(String.format("会员卡基本信息的使用日期为:%s，fixedTerm和fixedBeginTerm不能为空",
                    dateInfoType.getDescription()));
        }

        //固定期限
        if (dateInfoType == DateInfoType.DATE_TYPE_FIX_TIME_RANGE
                && (dateInfo.getBeginTimestamp() == null || dateInfo.getEndTimestamp() == null)) {
            return WxMpCardCreateResult.failure(String.format("会员卡基本信息的使用日期为:%s，beginTimestamp 和 endTimestamp 不能为空",
                    dateInfoType.getDescription()));
        }
        if (dateInfoType == DateInfoType.DATE_TYPE_FIX_TIME_RANGE
                && (dateInfo.getBeginTimestamp() * 1000 < System.currentTimeMillis()
                || dateInfo.getEndTimestamp() * 1000 < System.currentTimeMillis()
                || dateInfo.getBeginTimestamp() > dateInfo.getEndTimestamp())) {
            return WxMpCardCreateResult.failure(String.format("会员卡基本信息的使用日期为:%s，beginTimestamp和endTimestamp的值不合法，请检查",
                    dateInfoType.getDescription()));
        }

        if (!baseInfo.isUseAllLocations() && StringUtils.isBlank(baseInfo.getLocationIdList())) {
            return WxMpCardCreateResult.failure("会员卡基本信息的门店使用范围选择指定门店,门店列表:locationIdList不能为空");
        }

        //校验高级信息
        AdvancedInfo advancedInfo = memberCard.getAdvancedInfo();
        if (advancedInfo != null) {
            if (advancedInfo.getBusinessServiceList() != null) {
                for (String bs : advancedInfo.getBusinessServiceList()) {
                    try {
                        BusinessServiceType.valueOf(bs);
                    } catch (IllegalArgumentException ex) {
                        return WxMpCardCreateResult.failure("会员卡高级信息的商户服务:" + bs + " 不合法");
                    }
                }
            }
        }

        return WxMpCardCreateResult.success();
    }

    /**
     * 会员卡激活接口
     *
     * @param activatedMessage 激活所需参数
     * @return 调用返回的JSON字符串。
     * @throws WxErrorException 接口调用失败抛出的异常
     */
    @Override
    public String activateMemberCard(WxMpMemberCardActivatedMessage activatedMessage) throws WxErrorException {
        return this.wxMpService.post(MEMBER_CARD_ACTIVATE, GSON.toJson(activatedMessage));
    }

    /**
     * 拉取会员信息接口
     *
     * @param cardId 会员卡的CardId，微信分配
     * @param code   领取会员的会员卡Code
     * @return 会员信息的结果对象
     * @throws WxErrorException 接口调用失败抛出的异常
     */
    @Override
    public WxMpMemberCardUserInfoResult getUserInfo(String cardId, String code) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("card_id", cardId);
        jsonObject.addProperty("code", code);

        String responseContent = this.getWxMpService().post(MEMBER_CARD_USER_INFO_GET, jsonObject.toString());
        log.debug("{}", responseContent);

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpMemberCardUserInfoResult>() {
                }.getType());
    }

    /**
     * 当会员持卡消费后，支持开发者调用该接口更新会员信息。会员卡交易后的每次信息变更需通过该接口通知微信，便于后续消息通知及其他扩展功能。
     * <p>
     * 1.开发者可以同时传入add_bonus和bonus解决由于同步失败带来的幂等性问题。同时传入add_bonus和bonus时
     * add_bonus作为积分变动消息中的变量值，而bonus作为卡面上的总积分额度显示。余额变动同理。
     * 2.开发者可以传入is_notify_bonus控制特殊的积分对账变动不发送消息，余额变动同理。
     *
     * @param updateUserMessage 更新会员信息所需字段消息
     * @return 调用返回的JSON字符串。
     * @throws WxErrorException 接口调用失败抛出的异常
     */
    @Override
    public WxMpMemberCardUpdateResult updateUserMemberCard(WxMpMemberCardUpdateMessage updateUserMessage)
            throws WxErrorException {

        String responseContent = this.getWxMpService().post(MEMBER_CARD_UPDATE_USER, GSON.toJson(updateUserMessage));

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent),
                new TypeToken<WxMpMemberCardUpdateResult>() {
                }.getType());
    }

    @Override
    public MemberCardActivateUserFormResult setActivateUserForm(MemberCardActivateUserFormRequest userFormRequest) throws WxErrorException {
        String responseContent = this.getWxMpService().post(MEMBER_CARD_ACTIVATEUSERFORM, GSON.toJson(userFormRequest));
        return MemberCardActivateUserFormResult.fromJson(responseContent);
    }

    @Override
    public ActivatePluginParam getActivatePluginParam(String cardId, String outStr) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("card_id", cardId);
        params.addProperty("outer_str", outStr);
        String response = this.wxMpService.post(MEMBER_CARD_ACTIVATE_URL, GSON.toJson(params));
        ActivatePluginParamResult result = GSON.fromJson(response, ActivatePluginParamResult.class);
        if (0 == result.getErrcode()) {
            String url = result.getUrl();
            try {
                String decodedUrl = URLDecoder.decode(url, "UTF-8");
                Map<String, String> resultMap = parseRequestUrl(decodedUrl);
                ActivatePluginParam activatePluginParam = new ActivatePluginParam();
                activatePluginParam.setEncryptCardId(resultMap.get("encrypt_card_id"));
                activatePluginParam.setOuterStr(resultMap.get("outer_str"));
                activatePluginParam.setBiz(resultMap.get("biz") + "==");
                return activatePluginParam;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public CardUpdateResult updateCardInfo(MemberCardUpdateRequest memberCardUpdateRequest) throws WxErrorException {
        String response = this.wxMpService.post(MEMBER_CARD_UPDATE, GSON.toJson(memberCardUpdateRequest));
        return GSON.fromJson(response, CardUpdateResult.class);
    }

    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit;
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    public static Map<String, String> parseRequestUrl(String url) {
        Map<String, String> mapRequest = new HashMap<>(16);

        String[] arrSplit;

        String strUrlParam = truncateUrlPage(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (!"".equals(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
}
