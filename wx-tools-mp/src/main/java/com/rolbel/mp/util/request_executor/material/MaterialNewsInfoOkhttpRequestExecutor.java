package com.rolbel.mp.util.request_executor.material;

import com.google.common.collect.ImmutableMap;
import com.rolbel.common.WxType;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.okhttp.OkHttpProxyInfo;
import com.rolbel.common.util.json.WxGsonBuilder;
import com.rolbel.mp.bean.material.WxMpMaterialNews;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class MaterialNewsInfoOkhttpRequestExecutor extends MaterialNewsInfoRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MaterialNewsInfoOkhttpRequestExecutor(RequestHttp requestHttp) {
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
