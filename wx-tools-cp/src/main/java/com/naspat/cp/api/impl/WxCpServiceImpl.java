package com.naspat.cp.api.impl;

import com.naspat.common.bean.WxAccessToken;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.DefaultHttpClientBuilder;
import com.naspat.common.util.http.HttpClientBuilder;
import com.naspat.cp.config.WxCpConfigStorage;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * <pre>
 *  默认接口实现类，使用apache httpclient实现
 * Created by Binary Wang on 2017-5-27.
 * </pre>
 */
public class WxCpServiceImpl extends WxCpServiceAbstractImpl {
    protected CloseableHttpClient httpClient;
    protected HttpHost httpProxy;

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        if (this.configStorage.isAccessTokenExpired() || forceRefresh) {
            synchronized (this.globalAccessTokenRefreshLock) {
                if (this.configStorage.isAccessTokenExpired()) {
                    String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?"
                            + "&corpid=" + this.configStorage.getCorpId()
                            + "&corpsecret=" + this.configStorage.getCorpSecret();
                    try {
                        HttpGet httpGet = new HttpGet(url);
                        if (this.httpProxy != null) {
                            RequestConfig config = RequestConfig.custom()
                                    .setProxy(this.httpProxy).build();
                            httpGet.setConfig(config);
                        }
                        String resultContent = null;
                        try (CloseableHttpClient httpclient = getRequestHttpClient();
                             CloseableHttpResponse response = httpclient.execute(httpGet)) {
                            resultContent = new BasicResponseHandler().handleResponse(response);
                        } finally {
                            httpGet.releaseConnection();
                        }
                        WxError error = WxError.fromJson(resultContent);
                        if (error.getErrorCode() != 0) {
                            throw new WxErrorException(error);
                        }
                        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                        this.configStorage.updateAccessToken(
                                accessToken.getAccessToken(), accessToken.getExpiresIn());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return this.configStorage.getAccessToken();
    }

    @Override
    public void initHttp() {
        HttpClientBuilder httpClientBuilder = this.configStorage.getHttpClientBuilder();
        if (null == httpClientBuilder) {
            httpClientBuilder = DefaultHttpClientBuilder.get();
        }

        httpClientBuilder.httpProxyHost(this.configStorage.getHttpProxyHost())
                .httpProxyPort(this.configStorage.getHttpProxyPort())
                .httpProxyUsername(this.configStorage.getHttpProxyUsername())
                .httpProxyPassword(this.configStorage.getHttpProxyPassword());

        if (this.configStorage.getHttpProxyHost() != null && this.configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(this.configStorage.getHttpProxyHost(), this.configStorage.getHttpProxyPort());
        }

        this.httpClient = httpClientBuilder.build();
    }

    @Override
    public WxCpConfigStorage getWxCpConfigStorage() {
        return this.configStorage;
    }

}
