package com.naspat.mp.util.request_executor.material;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.okhttp.OkHttpProxyInfo;
import okhttp3.*;

import java.io.IOException;

public class MaterialDeleteOkhttpRequestExecutor extends MaterialDeleteRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public MaterialDeleteOkhttpRequestExecutor(RequestHttp<OkHttpClient, OkHttpProxyInfo> requestHttp) {
        super(requestHttp);
    }

    @Override
    public Boolean execute(String uri, String materialId) throws WxErrorException, IOException {
        this.logger.debug("MaterialDeleteOkhttpRequestExecutor is running");
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
            return true;
        }
    }
}
