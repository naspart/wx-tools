package com.rolbel.mp.util.request_executor.qrcode;

import com.rolbel.common.WxType;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.fs.FileUtil;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.mp.bean.WxMpQrCodeTicket;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.net.MimeTypes;
import jodd.util.StringPool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

public class QrCodeJoddHttpRequestExecutor extends QrCodeRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public QrCodeJoddHttpRequestExecutor(RequestHttp<HttpConnectionProvider, ProxyInfo> requestHttp) {
        super(requestHttp);
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

        HttpRequest request = HttpRequest.get(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
        }
        request.withConnectionProvider(requestHttp.getRequestHttpClient());

        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);
        String contentTypeHeader = response.header("Content-Type");
        if (MimeTypes.MIME_TEXT_PLAIN.equals(contentTypeHeader)) {
            String responseContent = response.bodyText();
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
        }
        try (InputStream inputStream = new ByteArrayInputStream(response.bodyBytes())) {
            return FileUtil.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        }
    }
}
