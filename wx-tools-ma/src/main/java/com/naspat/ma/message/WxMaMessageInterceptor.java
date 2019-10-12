package com.naspat.ma.message;

import com.naspat.ma.api.WxMaService;
import com.naspat.ma.bean.WxMaMessage;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.session.WxSessionManager;

import java.util.Map;

/**
 * 微信消息拦截器，可以用来做验证
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
