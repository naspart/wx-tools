package com.rolbel.mp.api.impl;

import com.rolbel.common.bean.WxAccessToken;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import com.rolbel.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.config.WxMpConfig;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

public class WxMpServiceHttpClientImpl extends WxMpServiceBaseImpl<CloseableHttpClient, HttpHost> {
    private CloseableHttpClient httpClient;
    private HttpHost httpProxy;

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.APACHE_HTTP;
    }

    @Override
    public void initHttp() {
        WxMpConfig wxMpConfig = this.getWxMpConfig();
        ApacheHttpClientBuilder apacheHttpClientBuilder = wxMpConfig.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }

        apacheHttpClientBuilder.httpProxyHost(wxMpConfig.getHttpProxyHost())
                .httpProxyPort(wxMpConfig.getHttpProxyPort())
                .httpProxyUsername(wxMpConfig.getHttpProxyUsername())
                .httpProxyPassword(wxMpConfig.getHttpProxyPassword());

        if (wxMpConfig.getHttpProxyHost() != null && wxMpConfig.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(wxMpConfig.getHttpProxyHost(), wxMpConfig.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        Lock lock = this.getWxMpConfig().getAccessTokenLock();
        try {
            lock.lock();
            if (this.getWxMpConfig().isAccessTokenExpired() || forceRefresh) {
                String url = String.format(WxMpService.GET_ACCESS_TOKEN_URL, this.getWxMpConfig().getAppId(), this.getWxMpConfig().getSecret());
                try {
                    HttpGet httpGet = new HttpGet(url);
                    if (this.getRequestHttpProxy() != null) {
                        RequestConfig config = RequestConfig.custom().setProxy(this.getRequestHttpProxy()).build();
                        httpGet.setConfig(config);
                    }
                    try (CloseableHttpResponse response = getRequestHttpClient().execute(httpGet)) {
                        String resultContent = new BasicResponseHandler().handleResponse(response);
                        WxError error = WxError.fromJson(resultContent);
                        if (error.getErrorCode() != 0) {
                            this.logger.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", url, null, error);
                            throw new WxErrorException(error);
                        }

                        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                        this.getWxMpConfig().updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
                    } finally {
                        httpGet.releaseConnection();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }

        return this.getWxMpConfig().getAccessToken();
    }
}
