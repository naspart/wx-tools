package com.rolbel.ma.api;

import com.rolbel.common.bean.WxJsapiSignature;
import com.rolbel.common.error.WxErrorException;

/**
 * <pre>
 *  jsapi相关接口
 * </pre>
 */
public interface WxMaJsapiService {
    /**
     * 获得jsapi_ticket的url
     */
    String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /**
     * 获得卡券api_ticket,不强制刷新api_ticket
     */
    String getCardApiTicket() throws WxErrorException;

    /**
     * 获得jsapi_ticket,不强制刷新jsapi_ticket
     */
    String getJsapiTicket() throws WxErrorException;

    /**
     * <pre>
     * 创建调用jsapi时所需要的签名
     *
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
     * </pre>
     */
    WxJsapiSignature createJsapiSignature(String url) throws WxErrorException;
}
