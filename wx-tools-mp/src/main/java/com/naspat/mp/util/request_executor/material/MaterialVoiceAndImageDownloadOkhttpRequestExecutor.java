package com.naspat.mp.util.request_executor.material;

import com.naspat.common.WxType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.common.util.http.okhttp.OkHttpProxyInfo;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.*;

public class MaterialVoiceAndImageDownloadOkhttpRequestExecutor extends MaterialVoiceAndImageDownloadRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public MaterialVoiceAndImageDownloadOkhttpRequestExecutor(RequestHttp<OkHttpClient, OkHttpProxyInfo> requestHttp, File tmpDirFile) {
        super(requestHttp, tmpDirFile);
    }

    @Override
    public InputStream execute(String uri, String materialId) throws WxErrorException, IOException {
        super.logger.debug("MaterialVoiceAndImageDownloadOkhttpRequestExecutor is running");
        OkHttpClient client = requestHttp.getRequestHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("media_id", materialId).build();
        Request request = new Request.Builder().url(uri).get().post(requestBody).build();
        Response response = client.newCall(request).execute();
        String contentTypeHeader = response.header("Content-Type");
        if ("text/plain".equals(contentTypeHeader)) {
            String responseContent = response.body().string();
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); BufferedSink sink = Okio.buffer(Okio.sink(outputStream))) {
            sink.writeAll(response.body().source());
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
    }
}
