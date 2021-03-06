package com.naspat.mp.api.impl;

import com.google.gson.JsonObject;
import com.naspat.common.error.WxErrorException;
import com.naspat.mp.api.WxMpMassMessageService;
import com.naspat.mp.api.WxMpService;
import com.naspat.mp.bean.*;

/**
 * <pre>
 *     群发消息服务类
 * </pre>
 */
public class WxMpMassMessageServiceImpl implements WxMpMassMessageService {
    private WxMpService wxMpService;

    WxMpMassMessageServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException {
        String responseContent = this.wxMpService.post(MEDIA_UPLOAD_NEWS_URL, news.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException {
        String responseContent = this.wxMpService.post(MEDIA_UPLOAD_VIDEO_URL, video.toJson());

        return WxMpMassUploadResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massGroupMessageSend(WxMpMassTagMessage message) throws WxErrorException {
        String responseContent = this.wxMpService.post(WxMpMassMessageService.MESSAGE_MASS_SENDALL_URL, message.toJson());

        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException {
        String responseContent = this.wxMpService.post(MESSAGE_MASS_SEND_URL, message.toJson());

        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massMessagePreview(WxMpMassPreviewMessage wxMpMassPreviewMessage) throws WxErrorException {
        String responseContent = this.wxMpService.post(MESSAGE_MASS_PREVIEW_URL, wxMpMassPreviewMessage.toJson());

        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public void delete(Long msgId, Integer articleIndex) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg_id", msgId);
        jsonObject.addProperty("article_idx", articleIndex);

        this.wxMpService.post(MESSAGE_MASS_DELETE_URL, jsonObject.toString());
    }
}
