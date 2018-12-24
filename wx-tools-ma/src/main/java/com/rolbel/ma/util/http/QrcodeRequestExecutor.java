package com.rolbel.ma.util.http;

import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.fs.FileUtils;
import com.rolbel.common.util.http.RequestExecutor;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.ResponseHandler;
import com.rolbel.common.util.http.apache.InputStreamResponseHandler;
import com.rolbel.common.util.http.apache.Utf8ResponseHandler;
import com.rolbel.ma.bean.AbstractWxMaQrcodeWrapper;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class QrcodeRequestExecutor implements RequestExecutor<File, AbstractWxMaQrcodeWrapper> {
    protected RequestHttp<CloseableHttpClient, HttpHost> requestHttp;

    public QrcodeRequestExecutor(RequestHttp<CloseableHttpClient, HttpHost> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, AbstractWxMaQrcodeWrapper data, ResponseHandler<File> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @Override
    public File execute(String uri, AbstractWxMaQrcodeWrapper ticket) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            httpPost.setConfig(
                    RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build()
            );
        }
        httpPost.setEntity(new StringEntity(ticket.toString()));

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost);
             InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            if (contentTypeHeader != null && contentTypeHeader.length > 0
                    && ContentType.APPLICATION_JSON.getMimeType()
                    .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
                String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                throw new WxErrorException(WxError.fromJson(responseContent));
            }

            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        } finally {
            httpPost.releaseConnection();
        }
    }
}
