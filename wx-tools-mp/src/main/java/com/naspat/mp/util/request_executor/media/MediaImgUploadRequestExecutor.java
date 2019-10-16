package com.naspat.mp.util.request_executor.media;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.mp.bean.material.WxMediaImgUploadResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.IOException;

public class MediaImgUploadRequestExecutor implements RequestExecutor<WxMediaImgUploadResult, File> {
    protected RequestHttp requestHttp;

    public MediaImgUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public WxMediaImgUploadResult execute(String uri, File data) throws WxErrorException, IOException {
        if (data == null) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("文件对象为空").build());
        }

        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("media", data)
                .setMode(HttpMultipartMode.RFC6532)
                .build();
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }

            return WxMediaImgUploadResult.fromJson(responseContent);
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, File data, ResponseHandler<WxMediaImgUploadResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<WxMediaImgUploadResult, File> create(RequestHttp requestHttp) {
        return new MediaImgUploadRequestExecutor(requestHttp);
    }
}
