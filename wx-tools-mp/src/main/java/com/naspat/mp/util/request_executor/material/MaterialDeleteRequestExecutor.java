package com.naspat.mp.util.request_executor.material;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.common.util.json.WxGsonBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaterialDeleteRequestExecutor implements RequestExecutor<Boolean, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected RequestHttp requestHttp;

    public MaterialDeleteRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
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
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            } else {
                return true;
            }
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<Boolean> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
        return new MaterialDeleteRequestExecutor(requestHttp);
    }
}
