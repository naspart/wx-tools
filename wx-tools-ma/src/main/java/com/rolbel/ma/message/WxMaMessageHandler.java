package com.rolbel.ma.message;

import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.bean.WxMaMessage;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.session.WxSessionManager;

import java.util.Map;

/**
 * 处理小程序推送消息的处理器接口
 */
public interface WxMaMessageHandler {

  void handle(WxMaMessage message, Map<String, Object> context,
              WxMaService service, WxSessionManager sessionManager) throws WxErrorException;

}
