package com.rolbel.mp.api;

import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.bean.user.WxMpUserBlacklistGetResult;

import java.util.List;


public interface WxMpUserBlacklistService {
    String GET_USER_BLACK_LIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist";

    String BATCH_ADD_BLACK_LIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist";

    String BATCH_DELETE_BLACK_LIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist";

    /**
     * <pre>
     * 获取公众号的黑名单列表
     * 详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
     * </pre>
     */
    WxMpUserBlacklistGetResult getBlacklist(String nextOpenid) throws WxErrorException;

    /**
     * <pre>
     *   拉黑用户
     *   详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
     * </pre>
     */
    void pushToBlacklist(List<String> openidList) throws WxErrorException;

    /**
     * <pre>
     *   取消拉黑用户
     *   详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
     * </pre>
     */
    void pullFromBlacklist(List<String> openidList) throws WxErrorException;
}
