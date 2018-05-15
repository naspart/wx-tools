package com.rolbel.mp.util.http;

import com.rolbel.common.bean.result.WxError;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.Utf8ResponseHandler;
import com.rolbel.common.util.json.WxGsonBuilder;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class ApacheMaterialDeleteRequestExecutor extends MaterialDeleteRequestExecutor<CloseableHttpClient, HttpHost> {
    public ApacheMaterialDeleteRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public Boolean execute(String uri, String materialId) throws WxErrorException, IOException {
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
            WxError error = WxError.fromJson(responseContent);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            } else {
                return true;
            }
        } finally {
            httpPost.releaseConnection();
        }
    }
}
