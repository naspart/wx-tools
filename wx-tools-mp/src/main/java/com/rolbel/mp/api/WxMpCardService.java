package com.rolbel.mp.api;

import com.rolbel.common.bean.WxCardApiSignature;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.bean.card.request.WxMpCard;
import com.rolbel.mp.bean.card.request.WxMpCardCreateLandingPage;
import com.rolbel.mp.bean.card.result.WxMpCardCreateCardResult;
import com.rolbel.mp.bean.card.result.WxMpCardCreateLandingPageResult;
import com.rolbel.mp.bean.card.result.WxMpCardGetUserCardResult;

/**
 * 卡券相关接口
 */
public interface WxMpCardService {
    String CARD_CREATE_URL = "https://api.weixin.qq.com/card/create";
    String CARD_PAYCELL_SET_URL = "https://api.weixin.qq.com/card/paycell/set";
    String CARD_SELF_CONSUME_CELL_SET_URL = "https://api.weixin.qq.com/card/selfconsumecell/set";
    String CARD_QRCODE_CREATE_URL = "https://api.weixin.qq.com/card/qrcode/create";
    String CARD_LANDING_PAGE_CREATE_URL = "https://api.weixin.qq.com/card/landingpage/create";
    String CARD_MP_NEWS_DISTRIBUTE_URL = "https://api.weixin.qq.com/card/mpnews/gethtml";
    String CARD_GET_DETAIL_URL = "https://api.weixin.qq.com/card/get";
    String CARD_GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=card";
    String CARD_GET_USER_CARD_LIST_URL = "https://api.weixin.qq.com/card/user/getcardlist";

    /**
     * 得到WxMpService
     */
    WxMpService getWxMpService();

    /**
     * <pre>
     *     创建卡券
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056
     * </pre>
     *
     * @param request 卡券详情
     * @return 卡券card_id
     * @throws WxErrorException
     */
    WxMpCardCreateCardResult createCard(WxMpCard request) throws WxErrorException;

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
    void setPayCell(String cardId, boolean isOpen) throws WxErrorException;

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
    void setSelfConsumeCell(String cardId, boolean isOpen, boolean needVerifyCode, boolean needRemarkAmount) throws WxErrorException;

    /**
     * <pre>
     *     创建货架接口
     *
     *     开发者需调用该接口创建货架链接，用于卡券投放。创建货架时需填写投放路径的场景字段。
     *
     *     详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025062
     * </pre>
     *
     * @param request 请求对象
     * @return 创建货架的结果
     * @throws WxErrorException
     */
    WxMpCardCreateLandingPageResult createCardLandingPage(WxMpCardCreateLandingPage request) throws WxErrorException;

    String wxMpNewsDistributeCard(String cardId) throws WxErrorException;

    /**
     * 获得卡券api_ticket，不强制刷新卡券api_ticket
     *
     * @return 卡券api_ticket
     * @see #getCardApiTicket(boolean)
     */
    String getCardApiTicket() throws WxErrorException;

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
     * @throws WxErrorException
     */
    String getCardApiTicket(boolean forceRefresh) throws WxErrorException;

    /**
     * <pre>
     * 创建调用卡券api时所需要的签名
     *
     * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     * </pre>
     *
     * @param optionalSignParam 参与签名的参数数组。
     *                          可以为下列字段：app_id, card_id, card_type, code, openid, location_id
     *                          </br>注意：当做wx.chooseCard调用时，必须传入app_id参与签名，否则会造成签名失败导致拉取卡券列表为空
     * @return 卡券Api签名对象
     */
    WxCardApiSignature createCardApiSignature(String... optionalSignParam) throws WxErrorException;

    /**
     * 查看卡券详情接口
     * 详见 https://mp.weixin.qq.com/wiki/14/8dd77aeaee85f922db5f8aa6386d385e.html#.E6.9F.A5.E7.9C.8B.E5.8D.A1.E5.88.B8.E8.AF.A6.E6.83.85
     *
     * @param cardId 卡券的ID
     * @return 返回的卡券详情JSON字符串
     * <br> [注] 由于返回的JSON格式过于复杂，难以定义其对应格式的Bean并且难以维护，因此只返回String格式的JSON串。
     * <br> 可由 com.google.gson.JsonParser#parse 等方法直接取JSON串中的某个字段。
     */
    String getCardDetail(String cardId) throws WxErrorException;

    WxMpCardGetUserCardResult getUserCardList(String openId, String cardId) throws WxErrorException;
}
