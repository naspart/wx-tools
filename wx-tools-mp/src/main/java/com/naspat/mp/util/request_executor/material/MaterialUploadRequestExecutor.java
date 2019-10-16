package com.naspat.mp.util.request_executor.material;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.mp.bean.material.WxMpMaterial;
import com.naspat.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class MaterialUploadRequestExecutor implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {
    protected RequestHttp requestHttp;

    public MaterialUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public WxMpMaterialUploadResult execute(String uri, WxMpMaterial material) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig response = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(response);
        }

        if (material == null) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("非法请求，material参数为空").build());
        }

        File file = material.getFile();
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder
                .addBinaryBody("media", file)
                .setMode(HttpMultipartMode.RFC6532);
        Map<String, String> form = material.getForm();
        if (material.getForm() != null) {
            multipartEntityBuilder.addPart("description",
                    new StringBody(WxGsonBuilder.create().toJson(form), ContentType.create("text/plain", Consts.UTF_8)));
        }

        httpPost.setEntity(multipartEntityBuilder.build());
        httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            } else {
                return WxMpMaterialUploadResult.fromJson(responseContent);
            }
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, WxMpMaterial data, ResponseHandler<WxMpMaterialUploadResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> create(RequestHttp requestHttp) {
        return new MaterialUploadRequestExecutor(requestHttp);
    }
}
