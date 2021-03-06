package com.naspat.mp.api.impl;

import com.google.gson.JsonObject;
import com.naspat.common.bean.result.WxMediaUploadResult;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.MediaUploadRequestExecutor;
import com.naspat.mp.api.WxMpKefuService;
import com.naspat.mp.api.WxMpService;
import com.naspat.mp.bean.kefu.*;

import java.io.File;
import java.util.Date;

public class WxMpKefuServiceImpl implements WxMpKefuService {
    private WxMpService wxMpService;

    WxMpKefuServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public boolean sendKefuMessage(WxMpKefuMessage message) throws WxErrorException {
        String responseContent = this.wxMpService.post(MESSAGE_CUSTOM_SEND, message.toJson());
        return responseContent != null;
    }

    @Override
    public WxMpKfList kfList() throws WxErrorException {
        String responseContent = this.wxMpService.get(GET_KF_LIST, null);
        return WxMpKfList.fromJson(responseContent);
    }

    @Override
    public WxMpKfOnlineList kfOnlineList() throws WxErrorException {
        String responseContent = this.wxMpService.get(GET_ONLINE_KF_LIST, null);
        return WxMpKfOnlineList.fromJson(responseContent);
    }

    @Override
    public boolean kfAccountAdd(WxMpKfAccountRequest request) throws WxErrorException {
        String responseContent = this.wxMpService.post(KF_ACCOUNT_ADD, request.toJson());
        return responseContent != null;
    }

    @Override
    public boolean kfAccountUpdate(WxMpKfAccountRequest request) throws WxErrorException {
        String responseContent = this.wxMpService.post(KF_ACCOUNT_UPDATE, request.toJson());
        return responseContent != null;
    }

    @Override
    public boolean kfAccountInviteWorker(WxMpKfAccountRequest request) throws WxErrorException {
        String responseContent = this.wxMpService.post(KF_ACCOUNT_INVITE_WORKER, request.toJson());
        return responseContent != null;
    }

    @Override
    public boolean kfAccountUploadHeadImg(String kfAccount, File imgFile) throws WxErrorException {
        WxMediaUploadResult responseContent = this.wxMpService
                .execute(MediaUploadRequestExecutor.create(this.wxMpService.getRequestHttp()), String.format(KF_ACCOUNT_UPLOAD_HEAD_IMG, kfAccount), imgFile);
        return responseContent != null;
    }

    @Override
    public boolean kfAccountDel(String kfAccount) throws WxErrorException {
        String responseContent = this.wxMpService.get(String.format(KF_ACCOUNT_DEL, kfAccount), null);
        return responseContent != null;
    }

    @Override
    public boolean kfSessionCreate(String openid, String kfAccount) throws WxErrorException {
        WxMpKfSessionRequest request = new WxMpKfSessionRequest(kfAccount, openid);
        String responseContent = this.wxMpService.post(KF_SESSION_CREATE, request.toJson());
        return responseContent != null;
    }

    @Override
    public boolean kfSessionClose(String openid, String kfAccount) throws WxErrorException {
        WxMpKfSessionRequest request = new WxMpKfSessionRequest(kfAccount, openid);
        String responseContent = this.wxMpService.post(KF_SESSION_CLOSE, request.toJson());
        return responseContent != null;
    }

    @Override
    public WxMpKfSessionGetResult kfSessionGet(String openid) throws WxErrorException {
        String responseContent = this.wxMpService.get(String.format(KF_SESSION_GET_SESSION, openid), null);
        return WxMpKfSessionGetResult.fromJson(responseContent);
    }

    @Override
    public WxMpKfSessionList kfSessionList(String kfAccount) throws WxErrorException {
        String responseContent = this.wxMpService.get(String.format(KF_SESSION_GET_SESSION_LIST, kfAccount), null);
        return WxMpKfSessionList.fromJson(responseContent);
    }

    @Override
    public WxMpKfSessionWaitCaseList kfSessionGetWaitCase() throws WxErrorException {
        String responseContent = this.wxMpService.get(KF_SESSION_GET_WAIT_CASE, null);
        return WxMpKfSessionWaitCaseList.fromJson(responseContent);
    }

    @Override
    public WxMpKfMsgList kfMsgList(Date startTime, Date endTime, Long msgId, Integer number) throws WxErrorException {
        if (number > 10000) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("非法参数请求，每次最多查询10000条记录！").build());
        }

        if (startTime.after(endTime)) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("起始时间不能晚于结束时间！").build());
        }

        JsonObject param = new JsonObject();
        param.addProperty("starttime", startTime.getTime() / 1000);
        param.addProperty("endtime", endTime.getTime() / 1000);
        param.addProperty("msgid", msgId);
        param.addProperty("number", number);

        String responseContent = this.wxMpService.post(MSG_RECORD_LIST, param.toString());

        return WxMpKfMsgList.fromJson(responseContent);
    }

    @Override
    public WxMpKfMsgList kfMsgList(Date startTime, Date endTime) throws WxErrorException {
        int number = 10000;
        WxMpKfMsgList result = this.kfMsgList(startTime, endTime, 1L, number);

        if (result != null && result.getNumber() == number) {
            Long msgId = result.getMsgId();
            WxMpKfMsgList followingResult = this.kfMsgList(startTime, endTime, msgId, number);
            while (followingResult != null && followingResult.getRecords().size() > 0) {
                result.getRecords().addAll(followingResult.getRecords());
                result.setNumber(result.getNumber() + followingResult.getNumber());
                result.setMsgId(followingResult.getMsgId());
                followingResult = this.kfMsgList(startTime, endTime, followingResult.getMsgId(), number);
            }
        }

        return result;
    }

    @Override
    public boolean sendKfTypingState(String openid, String command) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("touser", openid);
        params.addProperty("command", command);
        String responseContent = this.wxMpService.post(CUSTOM_TYPING, params.toString());

        return responseContent != null;
    }
}
