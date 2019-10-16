package com.naspat.common.util.http;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 */
public class SimpleGetRequestExecutor implements RequestExecutor<String, String> {
    protected RequestHttp requestHttp;

    public SimpleGetRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public String execute(String uri, String data) throws WxErrorException, IOException {
        if (data != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? data : '&' + data;
        }
        HttpGet httpGet = new HttpGet(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpGet.setConfig(config);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            return responseContent;
        } finally {
            httpGet.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        return new SimpleGetRequestExecutor(requestHttp);
    }
}
