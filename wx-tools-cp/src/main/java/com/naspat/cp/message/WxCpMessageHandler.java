package com.naspat.cp.message;

import com.naspat.common.error.WxErrorException;
import com.naspat.common.session.WxSessionManager;
import com.naspat.cp.api.WxCpService;
import com.naspat.cp.bean.WxCpXmlMessage;
import com.naspat.cp.bean.WxCpXmlOutMessage;

import java.util.Map;

/**
 * 处理微信推送消息的处理器接口
 */
public interface WxCpMessageHandler {

    /**
     * @param wxMessage
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param wxCpService
     * @param sessionManager
     * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
     */
    WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                             Map<String, Object> context,
                             WxCpService wxCpService,
                             WxSessionManager sessionManager) throws WxErrorException;

}
