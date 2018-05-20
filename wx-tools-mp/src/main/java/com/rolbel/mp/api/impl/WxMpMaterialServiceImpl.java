package com.rolbel.mp.api.impl;

import com.rolbel.common.api.WxConstant;
import com.rolbel.common.bean.result.WxError;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.util.fs.FileUtil;
import com.rolbel.common.util.json.WxGsonBuilder;
import com.rolbel.mp.api.WxMpMaterialService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.material.*;
import com.rolbel.mp.bean.result.WxMediaUploadResult;
import com.rolbel.mp.util.http.*;
import com.rolbel.mp.util.json.WxMpGsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WxMpMaterialServiceImpl implements WxMpMaterialService {

    private WxMpService wxMpService;

    public WxMpMaterialServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException {
        File tmpFile = null;
        try {
            tmpFile = FileUtil.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType);
            return this.mediaUpload(mediaType, tmpFile);
        } catch (IOException e) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg(e.getMessage()).build(), e);
        } finally {
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }
    }

    @Override
    public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
        String url = String.format(MEDIA_UPLOAD_URL, mediaType);
        return this.wxMpService.execute(MediaUploadRequestExecutor.create(this.wxMpService.getRequestHttp()), url, file);
    }

    @Override
    public File mediaDownload(String mediaId) throws WxErrorException {
        return this.wxMpService.execute(
                BaseMediaDownloadRequestExecutor.create(this.wxMpService.getRequestHttp(), this.wxMpService.getWxMpConfigStorage().getTmpDirFile()),
                MEDIA_GET_URL,
                "media_id=" + mediaId);
    }

    @Override
    public WxMediaImgUploadResult mediaImgUpload(File file) throws WxErrorException {
        return this.wxMpService.execute(MediaImgUploadRequestExecutor.create(this.wxMpService.getRequestHttp()), IMG_UPLOAD_URL, file);
    }

    @Override
    public WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial material) throws WxErrorException {
        String url = String.format(MATERIAL_ADD_URL, mediaType);
        return this.wxMpService.execute(MaterialUploadRequestExecutor.create(this.wxMpService.getRequestHttp()), url, material);
    }

    @Override
    public WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews news) throws WxErrorException {
        if (news == null || news.isEmpty()) {
            throw new IllegalArgumentException("news is empty!");
        }
        String responseContent = this.wxMpService.post(NEWS_ADD_URL, news.toJson());
        return WxMpMaterialUploadResult.fromJson(responseContent);
    }

    @Override
    public InputStream materialImageOrVoiceDownload(String mediaId) throws WxErrorException {
        return this.wxMpService.execute(MaterialVoiceAndImageDownloadRequestExecutor
                .create(this.wxMpService.getRequestHttp(), this.wxMpService.getWxMpConfigStorage().getTmpDirFile()), MATERIAL_GET_URL, mediaId);
    }

    @Override
    public WxMpMaterialVideoInfoResult materialVideoInfo(String mediaId) throws WxErrorException {
        return this.wxMpService.execute(MaterialVideoInfoRequestExecutor.create(this.wxMpService.getRequestHttp()), MATERIAL_GET_URL, mediaId);
    }

    @Override
    public WxMpMaterialNews materialNewsInfo(String mediaId) throws WxErrorException {
        return this.wxMpService.execute(MaterialNewsInfoRequestExecutor.create(this.wxMpService.getRequestHttp()), MATERIAL_GET_URL, mediaId);
    }

    @Override
    public boolean materialNewsUpdate(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
        String responseText = this.wxMpService.post(NEWS_UPDATE_URL, wxMpMaterialArticleUpdate.toJson());
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrorCode() == 0) {
            return true;
        } else {
            throw new WxErrorException(wxError);
        }
    }

    @Override
    public boolean materialDelete(String mediaId) throws WxErrorException {
        return this.wxMpService.execute(MaterialDeleteRequestExecutor.create(this.wxMpService.getRequestHttp()), MATERIAL_DEL_URL, mediaId);
    }

    @Override
    public WxMpMaterialCountResult materialCount() throws WxErrorException {
        String responseText = this.wxMpService.get(MATERIAL_GET_COUNT_URL, null);
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrorCode() == 0) {
            return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialCountResult.class);
        } else {
            throw new WxErrorException(wxError);
        }
    }

    @Override
    public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int offset, int count) throws WxErrorException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", WxConstant.MaterialType.NEWS);
        params.put("offset", offset);
        params.put("count", count);
        String responseText = this.wxMpService.post(MATERIAL_BATCHGET_URL, WxGsonBuilder.create().toJson(params));
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrorCode() == 0) {
            return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialNewsBatchGetResult.class);
        } else {
            throw new WxErrorException(wxError);
        }
    }

    @Override
    public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int offset, int count) throws WxErrorException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        String responseText = this.wxMpService.post(MATERIAL_BATCHGET_URL, WxGsonBuilder.create().toJson(params));
        WxError wxError = WxError.fromJson(responseText);
        if (wxError.getErrorCode() == 0) {
            return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialFileBatchGetResult.class);
        } else {
            throw new WxErrorException(wxError);
        }
    }
}
