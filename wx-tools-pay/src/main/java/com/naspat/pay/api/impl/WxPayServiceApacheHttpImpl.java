package com.naspat.pay.api.impl;

import com.naspat.pay.bean.WxPayApiData;
import com.naspat.pay.exception.WxPayException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * 微信支付请求实现类，apache httpclient实现.
 * </pre>
 */
public class WxPayServiceApacheHttpImpl extends BaseWxPayServiceImpl {
    @Override
    public byte[] postForBytes(String url, String requestStr, boolean useKey) throws WxPayException {
        try {
            HttpClientBuilder httpClientBuilder = createHttpClientBuilder(useKey);
            HttpPost httpPost = this.createHttpPost(url, requestStr);
            try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    final byte[] bytes = EntityUtils.toByteArray(response.getEntity());
                    final String responseData = Base64.encodeBase64String(bytes);
                    this.log.debug("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据(Base64编码后)】：{}", url, requestStr, responseData);
                    wxApiData.set(new WxPayApiData(url, requestStr, responseData, null));
                    return bytes;
                }
            } finally {
                httpPost.releaseConnection();
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
            wxApiData.set(new WxPayApiData(url, requestStr, null, e.getMessage()));
            throw new WxPayException(e.getMessage(), e);
        }
    }

    @Override
    public String post(String url, String requestStr, boolean useKey) throws WxPayException {
        try {
            HttpClientBuilder httpClientBuilder = this.createHttpClientBuilder(useKey);
            HttpPost httpPost = this.createHttpPost(url, requestStr);
            try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    this.log.debug("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}", url, requestStr, responseString);
                    if (this.getWxPayConfig().isIfSaveApiData()) {
                        wxApiData.set(new WxPayApiData(url, requestStr, responseString, null));
                    }

                    return responseString;
                }
            } finally {
                httpPost.releaseConnection();
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
            if (this.getWxPayConfig().isIfSaveApiData()) {
                wxApiData.set(new WxPayApiData(url, requestStr, null, null));
            }

            throw new WxPayException(e.getMessage(), e);
        }
    }

    private StringEntity createEntry(String requestStr) {
        try {
            return new StringEntity(new String(requestStr.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        } catch (UnsupportedEncodingException e) {
            //cannot happen
            this.log.error(e.getMessage(), e);
            return null;
        }
    }

    private HttpClientBuilder createHttpClientBuilder(boolean useKey) throws WxPayException {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (useKey) {
            this.initSSLContext(httpClientBuilder);
        }

        if (StringUtils.isNotBlank(this.getWxPayConfig().getHttpProxyHost()) && this.getWxPayConfig().getHttpProxyPort() > 0) {
            // 使用代理服务器 需要用户认证的代理服务器
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(
                    new AuthScope(this.getWxPayConfig().getHttpProxyHost(), this.getWxPayConfig().getHttpProxyPort()),
                    new UsernamePasswordCredentials(this.getWxPayConfig().getHttpProxyUsername(), this.getWxPayConfig().getHttpProxyPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(provider);
            httpClientBuilder.setProxy(new HttpHost(this.getWxPayConfig().getHttpProxyHost(), this.getWxPayConfig().getHttpProxyPort()));
        }

        return httpClientBuilder;
    }

    private HttpPost createHttpPost(String url, String requestStr) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(this.createEntry(requestStr));

        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(this.getWxPayConfig().getHttpConnectionTimeout())
                .setConnectTimeout(this.getWxPayConfig().getHttpConnectionTimeout())
                .setSocketTimeout(this.getWxPayConfig().getHttpTimeout())
                .build());

        return httpPost;
    }

    private void initSSLContext(HttpClientBuilder httpClientBuilder) throws WxPayException {
        SSLContext sslContext = this.getWxPayConfig().getSslContext();
        if (null == sslContext) {
            sslContext = this.getWxPayConfig().initSSLContext();
        }

        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());
        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
    }
}
