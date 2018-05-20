package com.rolbel.miniapp.message;

import com.rolbel.miniapp.api.WxMaService;
import com.rolbel.miniapp.bean.WxMaMessage;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.session.WxSessionManager;

import java.util.Map;

/**
 * 微信消息拦截器，可以用来做验证
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxMaMessageInterceptor {

  /**
   * 拦截微信消息
   *
   * @param context 上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @return true代表OK，false代表不OK
   */
  boolean intercept(WxMaMessage wxMessage,
                    Map<String, Object> context,
                    WxMaService wxMaService,
                    WxSessionManager sessionManager) throws WxErrorException;

}
