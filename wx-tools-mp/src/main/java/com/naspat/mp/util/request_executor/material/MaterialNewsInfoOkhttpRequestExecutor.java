package com.naspat.mp.util.request_executor.material;

import com.google.common.collect.ImmutableMap;
import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.okhttp.OkHttpProxyInfo;
import com.naspat.common.util.json.WxGsonBuilder;
import com.naspat.mp.bean.material.WxMpMaterialNews;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import okhttp3.*;

import java.io.IOException;

public class MaterialNewsInfoOkhttpRequestExecutor extends MaterialNewsInfoRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public MaterialNewsInfoOkhttpRequestExecutor(RequestHttp<OkHttpClient, OkHttpProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialNews execute(String uri, String materialId) throws WxErrorException, IOException {

        //得到httpClient
        OkHttpClient client = requestHttp.getRequestHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId)));
        Request request = new Request.Builder().url(uri).post(requestBody).build();

        Response response = client.newCall(request).execute();
        String responseContent = response.body().string();
        this.logger.debug("响应原始数据：{}", responseContent);

        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpGsonBuilder.create().fromJson(responseContent, WxMpMaterialNews.class);
        }
    }
}
