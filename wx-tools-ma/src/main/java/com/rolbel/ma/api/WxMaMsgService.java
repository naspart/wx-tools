package com.rolbel.ma.api;

import com.rolbel.ma.bean.WxMaKefuMessage;
import com.rolbel.ma.bean.WxMaTemplateMessage;
import com.rolbel.common.error.WxErrorException;

/**
 * <pre>
 * 消息发送接口
 * </pre>
 */
public interface WxMaMsgService {
  String KEFU_MESSAGE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
  String TEMPLATE_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";

  /**
   * <pre>
   * 发送客服消息
   * 详情请见: <a href="https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/conversation.html">发送客服消息</a>
   * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
   * </pre>
   */
  boolean sendKefuMsg(WxMaKefuMessage message) throws WxErrorException;

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: <a href="https://mp.weixin.qq.com/debug/wxadoc/dev/api/notice.html#接口说明">发送模板消息</a>
   * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
   * </pre>
   */
  void sendTemplateMsg(WxMaTemplateMessage templateMessage) throws WxErrorException;
}
