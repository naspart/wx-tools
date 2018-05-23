package com.rolbel.mp.api;

import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.bean.template.WxMpTemplate;
import com.rolbel.mp.bean.template.WxMpTemplateIndustry;
import com.rolbel.mp.bean.template.WxMpTemplateMessage;
import com.rolbel.mp.bean.template.result.WxMpTemplateAddResult;
import com.rolbel.mp.bean.template.result.WxMpTemplateSendMsgResult;

import java.util.List;

/**
 * <pre>
 * 模板消息接口
 * </pre>
 */
public interface WxMpTemplateMsgService {
    String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    String SET_TEMPLATE_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry";
    String GET_TEMPLATE_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry";
    String ADD_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template";
    String GET_ALL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template";
    String DELETE_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/del_private_template";

    /**
     * <pre>
     * 设置所属行业
     * 官方文档中暂未告知响应内容
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * </pre>
     *
     * @return 是否成功
     */
    void setIndustry(WxMpTemplateIndustry wxMpIndustry) throws WxErrorException;

    /***
     * <pre>
     * 获取设置的行业信息
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * </pre>
     *
     * @return wxMpIndustry
     */
    WxMpTemplateIndustry getIndustry() throws WxErrorException;

    /**
     * <pre>
     * 发送模板消息
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * </pre>
     *
     * @return 消息Id
     */
    WxMpTemplateSendMsgResult sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException;

    /**
     * <pre>
     * 获得模板ID
     * 从行业模板库选择模板到帐号后台，获得模板ID的过程可在MP中完成
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param shortTemplateId 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return templateId 模板Id
     */
    WxMpTemplateAddResult addTemplate(String shortTemplateId) throws WxErrorException;

    /**
     * <pre>
     * 获取模板列表
     * 获取已添加至帐号下所有模板列表，可在MP中查看模板列表信息，为方便第三方开发者，提供通过接口调用的方式来获取帐号下所有模板信息
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @return templateId 模板Id
     */
    List<WxMpTemplate> getAllPrivateTemplate() throws WxErrorException;

    /**
     * <pre>
     * 删除模板
     * 删除模板可在MP中完成，为方便第三方开发者，提供通过接口调用的方式来删除某帐号下的模板
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param templateId 模板Id
     */
    void delPrivateTemplate(String templateId) throws WxErrorException;
}
