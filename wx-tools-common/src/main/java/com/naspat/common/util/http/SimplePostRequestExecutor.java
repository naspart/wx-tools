package com.naspat.common.util.http;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * 用装饰模式实现
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 */
public class SimplePostRequestExecutor implements RequestExecutor<String, String> {
    protected RequestHttp requestHttp;

    public SimplePostRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public String execute(String uri, String data) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        if (data != null) {
            StringEntity entity = new StringEntity(data, Consts.UTF_8);
            httpPost.setEntity(entity);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            if (responseContent.isEmpty()) {
                throw new WxErrorException(WxError.builder().errorCode(9999).errorMsg("无响应内容").build());
            }

            if (responseContent.startsWith("<xml>")) {
                //xml格式输出直接返回
                return responseContent;
            }

            WxError error = WxError.fromJson(responseContent);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            return responseContent;
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        return new SimplePostRequestExecutor(requestHttp);
    }
}
