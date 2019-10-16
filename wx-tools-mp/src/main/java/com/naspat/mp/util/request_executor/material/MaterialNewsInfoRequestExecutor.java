package com.naspat.mp.util.request_executor.material;

import com.google.common.collect.ImmutableMap;
import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.mp.bean.material.WxMpMaterialNews;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MaterialNewsInfoRequestExecutor implements RequestExecutor<WxMpMaterialNews, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected RequestHttp requestHttp;

    public MaterialNewsInfoRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public WxMpMaterialNews execute(String uri, String materialId) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId))));
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            this.logger.debug("响应原始数据：{}", responseContent);
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            } else {
                return WxMpGsonBuilder.create().fromJson(responseContent, WxMpMaterialNews.class);
            }
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<WxMpMaterialNews> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @SuppressWarnings("unchecked")
    public static RequestExecutor<WxMpMaterialNews, String> create(RequestHttp requestHttp) {
        return new MaterialNewsInfoRequestExecutor(requestHttp);
    }
}
