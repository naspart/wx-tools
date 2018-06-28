package com.rolbel.mp.util.request_executor.material;

import com.rolbel.common.WxType;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.RequestHttp;
import com.rolbel.common.util.http.okhttp.OkHttpProxyInfo;
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
