package com.naspat.mp.util.request_executor.media;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.mp.bean.material.WxMediaImgUploadResult;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;

import java.io.File;
import java.io.IOException;

public class MediaImgUploadHttpRequestExecutor extends MediaImgUploadRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public MediaImgUploadHttpRequestExecutor(RequestHttp<HttpConnectionProvider, ProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMediaImgUploadResult execute(String uri, File data) throws WxErrorException, IOException {
        if (data == null) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("文件对象为空").build());
        }

        HttpRequest request = HttpRequest.post(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
        }
        request.withConnectionProvider(requestHttp.getRequestHttpClient());

        request.form("media", data);
        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);
        String responseContent = response.bodyText();
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }

        return WxMediaImgUploadResult.fromJson(responseContent);
    }
}
