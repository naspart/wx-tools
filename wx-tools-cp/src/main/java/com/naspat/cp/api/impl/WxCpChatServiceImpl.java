package com.naspat.cp.api.impl;

import com.google.gson.JsonParser;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.cp.api.WxCpChatService;
import com.naspat.cp.api.WxCpService;
import com.naspat.cp.bean.WxCpChat;
import com.naspat.cp.util.json.WxCpGsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群聊服务实现
 */
public class WxCpChatServiceImpl implements WxCpChatService {

    private final WxCpService internalService;

    /**
     * 创建群聊服务实现的实例
     *
     * @param internalService 企业微信的服务
     */
    public WxCpChatServiceImpl(WxCpService internalService) {
        this.internalService = internalService;
    }

    @Override
    public String chatCreate(String name, String owner, List<String> users, String chatId) throws WxErrorException {
        Map<String, Object> data = new HashMap<>(4);
        if (StringUtils.isNotBlank(name)) {
            data.put("name", name);
        }
        if (StringUtils.isNotBlank(owner)) {
            data.put("owner", owner);
        }
        if (users != null) {
            data.put("userlist", users);
        }
        if (StringUtils.isNotBlank(chatId)) {
            data.put("chatid", chatId);
        }
        String result = internalService.post("https://qyapi.weixin.qq.com/cgi-bin/appchat/create", WxGsonBuilder.create().toJson(data));
        return new JsonParser().parse(result).getAsJsonObject().get("chatid").getAsString();
    }

    @Override
    public void chatUpdate(String chatId, String name, String owner, List<String> usersToAdd, List<String> usersToDelete) throws WxErrorException {
        Map<String, Object> data = new HashMap<>(5);
        if (StringUtils.isNotBlank(chatId)) {
            data.put("chatid", chatId);
        }
        if (StringUtils.isNotBlank(name)) {
            data.put("name", name);
        }
        if (StringUtils.isNotBlank(owner)) {
            data.put("owner", owner);
        }
        if (usersToAdd != null && !usersToAdd.isEmpty()) {
            data.put("add_user_list", usersToAdd);
        }
        if (usersToDelete != null && !usersToDelete.isEmpty()) {
            data.put("del_user_list", usersToDelete);
        }
        internalService.post("https://qyapi.weixin.qq.com/cgi-bin/appchat/update", WxGsonBuilder.create().toJson(data));
    }

    @Override
    public WxCpChat chatGet(String chatId) throws WxErrorException {
        String result = internalService.get("https://qyapi.weixin.qq.com/cgi-bin/appchat/get?chatid=" + chatId, null);
        return WxCpGsonBuilder.create().fromJson(
                new JsonParser().parse(result).getAsJsonObject().getAsJsonObject("chat_info").toString(), WxCpChat.class);
    }

}
