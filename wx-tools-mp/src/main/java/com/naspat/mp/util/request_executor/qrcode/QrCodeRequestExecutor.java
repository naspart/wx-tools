package com.naspat.mp.util.request_executor.qrcode;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.fs.FileUtils;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.ResponseHandler;
import com.naspat.common.util.http.InputStreamResponseHandler;
import com.naspat.common.util.http.Utf8ResponseHandler;
import com.naspat.mp.bean.WxMpQrCodeTicket;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 获得QrCode图片 请求执行器
 */
public class QrCodeRequestExecutor implements RequestExecutor<File, WxMpQrCodeTicket> {
    protected RequestHttp requestHttp;

    public QrCodeRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public File execute(String uri, WxMpQrCodeTicket ticket) throws WxErrorException, IOException {
        if (ticket != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?")
                    ? "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8")
                    : "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
        }

        HttpGet httpGet = new HttpGet(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpGet.setConfig(config);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet);
             InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            if (contentTypeHeader != null && contentTypeHeader.length > 0) {
                // 出错
                if (ContentType.TEXT_PLAIN.getMimeType()
                        .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
                    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                    throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
                }
            }
            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        } finally {
            httpGet.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, WxMpQrCodeTicket data, ResponseHandler<File> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<File, WxMpQrCodeTicket> create(RequestHttp requestHttp) throws WxErrorException {
        return new QrCodeRequestExecutor(requestHttp);
    }
}
