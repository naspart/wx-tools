package com.rolbel.miniapp.message;

import com.rolbel.miniapp.api.WxMaService;
import com.rolbel.miniapp.bean.WxMaMessage;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.session.WxSessionManager;

import java.util.Map;

/**
 * 处理小程序推送消息的处理器接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxMaMessageHandler {

  void handle(WxMaMessage message, Map<String, Object> context,
              WxMaService service, WxSessionManager sessionManager) throws WxErrorException;

}
