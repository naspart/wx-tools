package com.naspat.ma.util.http;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.InputStreamResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.ma.bean.AbstractWxMaQrcodeWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.InputStream;

public class QrcodeBytesRequestExecutor implements RequestExecutor<byte[], AbstractWxMaQrcodeWrapper> {
    protected RequestHttp requestHttp;

    public QrcodeBytesRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, AbstractWxMaQrcodeWrapper data, ResponseHandler<byte[]> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    @Override
    public byte[] execute(String uri, AbstractWxMaQrcodeWrapper qrcodeWrapper) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            httpPost.setConfig(
                    RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build()
            );
        }

        httpPost.setEntity(new StringEntity(qrcodeWrapper.toJson()));

        try (final CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost);
             final InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            if (contentTypeHeader != null && contentTypeHeader.length > 0
                    && ContentType.APPLICATION_JSON.getMimeType()
                    .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
                String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                throw new WxErrorException(WxError.fromJson(responseContent));
            }

            return IOUtils.toByteArray(inputStream);
        } finally {
            httpPost.releaseConnection();
        }
    }
}
