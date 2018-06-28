package com.rolbel.mp.util.request_executor.material;

import com.rolbel.common.WxType;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.okhttp.OkHttpProxyInfo;
import com.rolbel.mp.bean.material.WxMpMaterialVideoInfoResult;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MaterialVideoInfoOkhttpRequestExecutor extends MaterialVideoInfoRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MaterialVideoInfoOkhttpRequestExecutor(RequestHttp<OkHttpClient, OkHttpProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialVideoInfoResult execute(String uri, String materialId) throws WxErrorException, IOException {
        logger.debug("MaterialVideoInfoOkhttpRequestExecutor is running");
        //得到httpClient
        OkHttpClient client = requestHttp.getRequestHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("media_id", materialId).build();
        Request request = new Request.Builder().url(uri).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String responseContent = response.body().string();
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpMaterialVideoInfoResult.fromJson(responseContent);
        }
    }
}
