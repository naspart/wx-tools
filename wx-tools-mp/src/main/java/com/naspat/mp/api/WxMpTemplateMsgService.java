package com.naspat.mp.api;

import com.naspat.common.error.WxErrorException;
import com.naspat.mp.bean.template.WxMpTemplate;
import com.naspat.mp.bean.template.WxMpTemplateIndustry;
import com.naspat.mp.bean.template.WxMpTemplateMessage;
import com.naspat.mp.bean.template.result.WxMpTemplateAddResult;
import com.naspat.mp.bean.template.result.WxMpTemplateSendMsgResult;

import java.util.List;

/**
 * <pre>
 * 接口{@code WxMpAiOpenService} 微信模板消息接口.
 *
 * 功能参考：<a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 模板消息接口</a>
 * </pre>
 */
public interface WxMpTemplateMsgService {
    /**
     * 发送模板消息接口URL
     */
    String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    /**
     * 设置所属行业接口URL
     */
    String SET_TEMPLATE_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry";

    /**
     * 获取设置的行业信息接口URL
     */
    String GET_TEMPLATE_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry";

    /**
     * 获得模板ID接口URL
     */
    String ADD_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template";

    /**
     * 获取模板列表接口URL
     */
    String GET_ALL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template";

    /**
     * 删除模板接口URL
     */
    String DELETE_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/del_private_template";

    /**
     * <pre>
     * 设置所属行业.
     *
     * 功能参考： <a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 设置所属行业</a>
     * 接口地址：{@code https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN}
     * 请求方式：{@code POST}
     * </pre>
     *
     * @param wxMpIndustry 所属行业
     * @throws WxErrorException 微信异常
     */
    void setIndustry(WxMpTemplateIndustry wxMpIndustry) throws WxErrorException;

    /**
     * <pre>
     * 获取设置的行业信息.
     *
     * 功能参考： <a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 获取设置的行业信息</a>
     * 接口地址：{@code https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN}
     * 请求方式：{@code POST}
     * </pre>
     *
     * @return wxMpIndustry
     * @throws WxErrorException 微信异常
     */
    WxMpTemplateIndustry getIndustry() throws WxErrorException;

    /**
     * <pre>
     * 发送模板消息.
     *
     * 例：{@code
     * WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
     *         .templateId("2zFV-V8XBvsWYb0iYQnlqRoUBdXJX9rMeXJy0Ts3SH8")
     *         .toUser("OPEN_ID")
     *         .build();
     *
     * wxMpTemplateMessage.addData(new WxMpTemplateData("first", "感谢您的使用", "#FF00FF"));
     * wxMpTemplateMessage.addData(new WxMpTemplateData("remark", RandomStringUtils.randomAlphanumeric(100), "#FF00FF"));
     * try {
     *     wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
     * } catch (WxErrorException e) {
     *     e.printStackTrace();
     * }}
     *
     * 功能参考： <a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 获取设置的行业信息</a>
     * 接口地址：{@code https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN}
     * 请求方式：{@code POST}
     * </pre>
     *
     * @param templateMessage 模板消息
     * @return 消息Id
     * @throws WxErrorException 微信异常
     */
    WxMpTemplateSendMsgResult sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException;

    /**
     * <pre>
     * 获得模板ID.
     *
     * 功能参考： <a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 获得模板ID</a>
     * 接口地址：{@code https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN}
     * 请求方式：{@code POST}
     * </pre>
     *
     * @param shortTemplateId 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return templateId 模板Id
     * @throws WxErrorException 微信异常
     */
    WxMpTemplateAddResult addTemplate(String shortTemplateId) throws WxErrorException;

    /**
     * <pre>
     * 获取模板列表.
     *
     * 获取已添加至帐号下所有模板列表，可在MP中查看模板列表信息，为方便第三方开发者，提供通过接口调用的方式来获取帐号下所有模板信息
     *
     * 功能参考： <a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 获取模板列表</a>
     * 接口地址：{@code https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN}
     * 请求方式：{@code POST}
     * </pre>
     *
     * @return 模板列表
     * @throws WxErrorException 微信异常
     */
    List<WxMpTemplate> getAllPrivateTemplate() throws WxErrorException;

    /**
     * <pre>
     * 删除模板.
     *
     * 功能参考： <a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 删除模板</a>
     * 接口地址：{@code https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN}
     * 请求方式：{@code POST}
     * </pre>
     *
     * @param templateId 模板Id
     * @throws WxErrorException 微信异常
     */
    void delPrivateTemplate(String templateId) throws WxErrorException;
}
