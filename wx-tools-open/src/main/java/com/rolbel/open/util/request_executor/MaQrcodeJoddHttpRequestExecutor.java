package com.rolbel.open.util.request_executor;

import com.rolbel.common.WxType;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.fs.FileUtils;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.open.bean.ma.WxMaQrcodeParam;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.net.MimeTypes;
import jodd.util.StringPool;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

public class MaQrcodeJoddHttpRequestExecutor extends MaQrcodeRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public MaQrcodeJoddHttpRequestExecutor(RequestHttp<HttpConnectionProvider, ProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public File execute(String uri, WxMaQrcodeParam qrcodeParam) throws WxErrorException, IOException {
        if (qrcodeParam != null && StringUtils.isNotBlank(qrcodeParam.getPagePath())) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?")
                    ? "path=" + URLEncoder.encode(qrcodeParam.getRequestPath(), "UTF-8")
                    : "&path=" + URLEncoder.encode(qrcodeParam.getRequestPath(), "UTF-8");
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
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
        }
        try (InputStream inputStream = new ByteArrayInputStream(response.bodyBytes())) {
            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        }
    }
}