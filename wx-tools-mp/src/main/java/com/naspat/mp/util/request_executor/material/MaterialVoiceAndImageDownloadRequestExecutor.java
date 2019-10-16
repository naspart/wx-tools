package com.naspat.mp.util.request_executor.material;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.InputStreamResponseHandler;
import com.naspat.common.util.json.WxGsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MaterialVoiceAndImageDownloadRequestExecutor implements RequestExecutor<InputStream, String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected RequestHttp requestHttp;
    protected File tmpDirFile;

    public MaterialVoiceAndImageDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
        this.requestHttp = requestHttp;
        this.tmpDirFile = tmpDirFile;
    }

    @Override
    public InputStream execute(String uri, String materialId) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        Map<String, String> params = new HashMap<>();
        params.put("media_id", materialId);
        httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost);
             InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
            // 下载媒体文件出错
            byte[] responseContent = IOUtils.toByteArray(inputStream);
            String responseContentString = new String(responseContent, StandardCharsets.UTF_8);
            if (responseContentString.length() < 100) {
                try {
                    WxError wxError = WxGsonBuilder.create().fromJson(responseContentString, WxError.class);
                    if (wxError.getErrorCode() != 0) {
                        throw new WxErrorException(wxError);
                    }
                } catch (com.google.gson.JsonSyntaxException ex) {
                    return new ByteArrayInputStream(responseContent);
                }
            }
            return new ByteArrayInputStream(responseContent);
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<InputStream> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<InputStream, String> create(RequestHttp requestHttp, File tmpDirFile) {
        return new MaterialVoiceAndImageDownloadRequestExecutor(requestHttp, tmpDirFile);
    }
}
