package com.naspat.common.util.http;

import com.naspat.common.bean.result.WxMediaUploadResult;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.IOException;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 */
public class MediaUploadRequestExecutor implements RequestExecutor<WxMediaUploadResult, File> {
    protected RequestHttp requestHttp;

    public MediaUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public WxMediaUploadResult execute(String uri, File file) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }
        if (file != null) {
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("media", file)
                    .setMode(HttpMultipartMode.RFC6532)
                    .build();
            httpPost.setEntity(entity);
        }
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            return WxMediaUploadResult.fromJson(responseContent);
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, File data, ResponseHandler<WxMediaUploadResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<WxMediaUploadResult, File> create(RequestHttp requestHttp) {
        return new MediaUploadRequestExecutor(requestHttp);
    }
}
