package com.rolbel.mp.util.request_executor.material;

import com.google.common.collect.ImmutableMap;
import com.rolbel.common.WxType;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.json.WxGsonBuilder;
import com.rolbel.mp.bean.material.WxMpMaterialNews;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;

import java.io.IOException;

public class MaterialNewsInfoJoddHttpRequestExecutor extends MaterialNewsInfoRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public MaterialNewsInfoJoddHttpRequestExecutor(RequestHttp<HttpConnectionProvider, ProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialNews execute(String uri, String materialId) throws WxErrorException, IOException {
        if (requestHttp.getRequestHttpProxy() != null) {
            requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
        }

        HttpRequest request = HttpRequest.post(uri)
                .withConnectionProvider(requestHttp.getRequestHttpClient())
                .body(WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId)));
        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);

        String responseContent = response.bodyText();
        super.logger.debug("响应原始数据：{}", responseContent);
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpGsonBuilder.create().fromJson(responseContent, WxMpMaterialNews.class);
        }
    }
}
