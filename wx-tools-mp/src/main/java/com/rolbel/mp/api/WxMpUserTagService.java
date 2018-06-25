package com.rolbel.mp.api;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.bean.tag.WxTagListUser;
import com.rolbel.mp.bean.tag.WxUserTag;

import java.util.List;

/**
 * 用户标签管理相关接口
 */
public interface WxMpUserTagService {
    String CREATE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/create";

    String GET_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/get";

    String UPDATE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/update";

    String DELETE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/delete";

    String GET_TAG_USER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/tag/get";

    String BATCH_SET_TAG_FOR_USERS_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";

    String BATCH_CANCEL_TAG_FOR_USERS_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";

    String GET_USER_TAG_LIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";

    /**
     * <pre>
     * 创建标签
     * 一个公众号，最多可以创建100个标签。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param name 标签名字（30个字符以内）
     */
    WxUserTag tagCreate(String name) throws WxErrorException;

    /**
     * <pre>
     * 获取公众号已创建的标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN
     * </pre>
     */
    List<WxUserTag> tagGet() throws WxErrorException;

    /**
     * <pre>
     * 编辑标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
     * </pre>
     */
    void tagUpdate(Long tagId, String name) throws WxErrorException;

    /**
     * <pre>
     * 删除标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
     * </pre>
     */
    void tagDelete(Long tagId) throws WxErrorException;

    /**
     * <pre>
     * 获取标签下粉丝列表
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN
     * </pre>
     */
    WxTagListUser tagListUser(Long tagId, String nextOpenId)
            throws WxErrorException;

    /**
     * <pre>
     * 批量为用户打标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN
     * </pre>
     */
    void batchTagging(Long tagId, String[] openIds) throws WxErrorException;

    /**
     * <pre>
     * 批量为用户取消标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN
     * </pre>
     */
    void batchUntagging(Long tagId, String[] openIds) throws WxErrorException;


    /**
     * <pre>
     * 获取用户身上的标签列表
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @return 标签Id的列表
     */
    List<Long> userTagList(String openId) throws WxErrorException;
}
