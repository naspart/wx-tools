package com.rolbel.mp.api.impl;

import com.rolbel.common.WxType;
import com.rolbel.common.bean.WxAccessToken;
import com.rolbel.common.error.WxError;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.common.util.http.HttpType;
import com.rolbel.common.util.http.apache.ApacheHttpClientBuilder;
import com.rolbel.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.rolbel.mp.config.WxMpConfig;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import static com.rolbel.mp.enums.WxMpApiUrl.Other.GET_ACCESS_TOKEN_URL;

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
        final WxMpConfig config = this.getWxMpConfig();
        if (!config.isAccessTokenExpired() && !forceRefresh) {
            return config.getAccessToken();
        }

        Lock lock = config.getAccessTokenLock();
        lock.lock();
        try {
            String url = String.format(GET_ACCESS_TOKEN_URL.getUrl(config), config.getAppId(), config.getSecret());
            try {
                HttpGet httpGet = new HttpGet(url);
                if (this.getRequestHttpProxy() != null) {
                    RequestConfig requestConfig = RequestConfig.custom().setProxy(this.getRequestHttpProxy()).build();
                    httpGet.setConfig(requestConfig);
                }
                try (CloseableHttpResponse response = getRequestHttpClient().execute(httpGet)) {
                    String resultContent = new BasicResponseHandler().handleResponse(response);
                    WxError error = WxError.fromJson(resultContent, WxType.MP);
                    if (error.getErrorCode() != 0) {
                        throw new WxErrorException(error);
                    }
                    WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                    config.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
                    return config.getAccessToken();
                } finally {
                    httpGet.releaseConnection();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }
}
