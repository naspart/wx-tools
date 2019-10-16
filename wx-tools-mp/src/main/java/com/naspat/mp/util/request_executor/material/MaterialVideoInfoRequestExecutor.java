package com.naspat.mp.util.request_executor.material;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.mp.bean.material.WxMpMaterialVideoInfoResult;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaterialVideoInfoRequestExecutor implements RequestExecutor<WxMpMaterialVideoInfoResult, String> {
    protected RequestHttp requestHttp;

    public MaterialVideoInfoRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public WxMpMaterialVideoInfoResult execute(String uri, String materialId) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        Map<String, String> params = new HashMap<>();
        params.put("media_id", materialId);
        httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            } else {
                return WxMpMaterialVideoInfoResult.fromJson(responseContent);
            }
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<WxMpMaterialVideoInfoResult> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<WxMpMaterialVideoInfoResult, String> create(RequestHttp requestHttp) {
        return new MaterialVideoInfoRequestExecutor(requestHttp);
    }
}
