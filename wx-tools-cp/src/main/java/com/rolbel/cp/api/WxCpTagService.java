package com.rolbel.cp.api;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.cp.bean.WxCpTag;
import com.rolbel.cp.bean.WxCpTagAddOrRemoveUsersResult;
import com.rolbel.cp.bean.WxCpTagGetResult;
import com.rolbel.cp.bean.WxCpUser;

import java.util.List;

/**
 * <pre>
 *  标签管理接口
 *  Created by BinaryWang on 2017/6/24.
 * </pre>

 */
public interface WxCpTagService {
    /**
     * 创建标签
     *
     * @param tagName 标签名
     */
    String create(String tagName) throws WxErrorException;

    /**
     * 更新标签
     *
     * @param tagId   标签id
     * @param tagName 标签名
     */
    void update(String tagId, String tagName) throws WxErrorException;

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    void delete(String tagId) throws WxErrorException;

    /**
     * 获得标签列表
     */
    List<WxCpTag> listAll() throws WxErrorException;

    /**
     * 获取标签成员
     *
     * @param tagId 标签ID
     */
    List<WxCpUser> listUsersByTagId(String tagId) throws WxErrorException;

    /**
     * 增加标签成员
     *
     * @param tagId   标签id
     * @param userIds 用户ID 列表
     */
    WxCpTagAddOrRemoveUsersResult addUsers2Tag(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException;

    /**
     * 移除标签成员
     *
     * @param tagId   标签id
     * @param userIds 用户id列表
     */
    WxCpTagAddOrRemoveUsersResult removeUsersFromTag(String tagId, List<String> userIds) throws WxErrorException;

    /**
     * 获取标签成员.
     * 对应: http://qydev.weixin.qq.com/wiki/index.php?title=管理标签 中的get接口
     *
     * @param tagId 标签id
     */
    WxCpTagGetResult get(String tagId) throws WxErrorException;
}
