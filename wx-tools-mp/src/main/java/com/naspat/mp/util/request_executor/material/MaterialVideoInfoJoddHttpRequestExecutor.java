package com.naspat.mp.util.request_executor.material;

import com.naspat.mp.bean.material.WxMpMaterialVideoInfoResult;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;
import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestHttp;

import java.io.IOException;

public class MaterialVideoInfoJoddHttpRequestExecutor extends MaterialVideoInfoRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public MaterialVideoInfoJoddHttpRequestExecutor(RequestHttp<HttpConnectionProvider, ProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialVideoInfoResult execute(String uri, String materialId) throws WxErrorException, IOException {
        HttpRequest request = HttpRequest.post(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
        }
        request.withConnectionProvider(requestHttp.getRequestHttpClient());

        request.query("media_id", materialId);
        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);
        String responseContent = response.bodyText();
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpMaterialVideoInfoResult.fromJson(responseContent);
        }
    }
}