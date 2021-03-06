package com.naspat.ma.api.impl;

import com.google.gson.JsonObject;
import com.naspat.common.bean.result.WxMediaUploadResult;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.MediaUploadRequestExecutor;
import com.naspat.ma.api.WxMaSecCheckService;
import com.naspat.ma.api.WxMaService;

import java.io.File;

public class WxMaSecCheckServiceImpl implements WxMaSecCheckService {
    private WxMaService service;

    public WxMaSecCheckServiceImpl(WxMaService service) {
        this.service = service;
    }

    @Override
    public boolean checkImage(File file) throws WxErrorException {
        //这里只是借用MediaUploadRequestExecutor，并不使用其返回值WxMediaUploadResult
        WxMediaUploadResult result = this.service.execute(MediaUploadRequestExecutor
                .create(this.service.getRequestHttp()), IMG_SEC_CHECK_URL, file);
        return result != null;
    }

    @Override
    public boolean checkMessage(String msgString) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("content", msgString);
        try {
            this.service.post(MSG_SEC_CHECK_URL, jsonObject.toString());
        } catch (WxErrorException e) {
            return false;
        }

        return true;
    }
}
