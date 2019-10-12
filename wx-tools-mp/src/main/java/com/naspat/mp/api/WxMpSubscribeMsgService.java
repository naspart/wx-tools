package com.naspat.mp.api;

import com.naspat.common.error.WxErrorException;
import com.naspat.mp.bean.subscribe.WxMpSubscribeMessage;

/**
 * <pre>
 * 一次性订阅消息接口
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1500374289_66bvB
 * </pre>
 */
public interface WxMpSubscribeMsgService {
    String SUBSCRIBE_MESSAGE_AUTHORIZE_URL = "https://mp.weixin.qq.com/mp/subscribemsg?action=get_confirm&appid=%s&scene=%d&template_id=%s&redirect_url=%s&reserved=%s#wechat_redirect";
    String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/subscribe";

    /**
     * <pre>
     * 构造用户订阅一条模板消息授权的url连接
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1500374289_66bvB
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param scene       重定向后会带上scene参数，开发者可以填0-10000的整形值，用来标识订阅场景值
     * @param reserved    用于保持请求和回调的状态，授权请后原样带回给第三方 (最多128字节，要求做urlencode)
     * @return url
     */
    String subscribeMsgAuthorizationUrl(String redirectURI, int scene, String reserved);

    /**
     * <pre>
     * 发送一次性订阅消息
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1500374289_66bvB
     * </pre>
     */
    void sendSubscribeMessage(WxMpSubscribeMessage message) throws WxErrorException;

}
