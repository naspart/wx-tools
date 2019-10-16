package com.naspat.common.util.http;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.fs.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 */
public class MediaDownloadRequestExecutor implements RequestExecutor<File, String> {
    protected RequestHttp requestHttp;
    protected File tmpDirFile;

    public MediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
        this.requestHttp = requestHttp;
        this.tmpDirFile = tmpDirFile;
    }

    @Override
    public File execute(String uri, String data) throws WxErrorException, IOException {
        if (data != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? data : '&' + data;
        }

        HttpGet httpGet = new HttpGet(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpGet.setConfig(config);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet);
             InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            if (contentTypeHeader != null && contentTypeHeader.length > 0) {
                if (contentTypeHeader[0].getValue().startsWith(ContentType.APPLICATION_JSON.getMimeType())) {
                    // application/json; encoding=utf-8 下载媒体文件出错
                    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                    throw new WxErrorException(WxError.fromJson(responseContent));
                }
            }

            String fileName = new HttpResponseProxy(response).getFileName();
            if (StringUtils.isBlank(fileName)) {
                return null;
            }

            return FileUtils.createTmpFile(inputStream, FilenameUtils.getBaseName(fileName), FilenameUtils.getExtension(fileName),
                    this.tmpDirFile);

        } finally {
            httpGet.releaseConnection();
        }
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<File> handler) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile) {
        return new MediaDownloadRequestExecutor(requestHttp, tmpDirFile);
    }
}
