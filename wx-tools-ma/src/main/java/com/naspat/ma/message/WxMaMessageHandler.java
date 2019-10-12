package com.naspat.ma.message;

import com.naspat.ma.api.WxMaService;
import com.naspat.ma.bean.WxMaMessage;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.session.WxSessionManager;

import java.util.Map;

/**
 * 处理小程序推送消息的处理器接口
 */
public interface WxMaMessageHandler {

  void handle(WxMaMessage message, Map<String, Object> context,
              WxMaService service, WxSessionManager sessionManager) throws WxErrorException;

}
