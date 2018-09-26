package com.rolbel.mp.api;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.bean.member_card.WxMpMemberCardActivatedMessage;
import com.rolbel.mp.bean.member_card.WxMpMemberCardUpdateMessage;
import com.rolbel.mp.bean.member_card.WxMpMemberCardUpdateResult;
import com.rolbel.mp.bean.member_card.WxMpMemberCardUserInfoResult;

/**
 * 会员卡相关接口
 *
 * @author YuJian(mgcnrx11 @ gmail.com)
 * @version 2017/7/8
 */
public interface WxMpMemberCardService {
    String MEMBER_CARD_ACTIVATE = "https://api.weixin.qq.com/card/member_card/activate";
    String MEMBER_CARD_USER_INFO_GET = "https://api.weixin.qq.com/card/member_card/userinfo/get";
    String MEMBER_CARD_UPDATE_USER = "https://api.weixin.qq.com/card/member_card/updateuser";

    /**
     * 会员卡激活之微信开卡接口(wx_activate=true情况调用)
     */
    String MEMBER_CARD_ACTIVATEUSERFORM = "https://api.weixin.qq.com/card/membercard/activateuserform/set";

    /**
     * 得到WxMpService
     */
    WxMpService getWxMpService();

    /**
     * 会员卡激活接口
     *
     * @param activatedMessage 激活所需参数
     * @return 调用返回的JSON字符串。
     * @throws WxErrorException 接口调用失败抛出的异常
     */
    String activateMemberCard(WxMpMemberCardActivatedMessage activatedMessage) throws WxErrorException;

    /**
     * 拉取会员信息接口
     *
     * @param cardId 会员卡的CardId，微信分配
     * @param code   领取会员的会员卡Code
     * @return 会员信息的结果对象
     * @throws WxErrorException 接口调用失败抛出的异常
     */
    WxMpMemberCardUserInfoResult getUserInfo(String cardId, String code) throws WxErrorException;

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
    WxMpMemberCardUpdateResult updateUserMemberCard(WxMpMemberCardUpdateMessage updateUserMessage) throws WxErrorException;
}
