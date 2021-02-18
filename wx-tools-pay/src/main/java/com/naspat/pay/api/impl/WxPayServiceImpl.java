package com.naspat.pay.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.naspat.common.util.json.GsonParser;
import com.naspat.pay.bean.WxPayApiData;
import com.naspat.pay.exception.WxPayException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * 微信支付接口请求实现类，默认使用Apache HttpClient实现
 * </pre>
 */
public class WxPayServiceImpl extends BaseWxPayServiceImpl {
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

    @Override
    public String postV3(String url, String requestStr) throws WxPayException {
        CloseableHttpClient httpClient = this.createApiV3HttpClient();
        HttpPost httpPost = this.createHttpPost(url, requestStr);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            //v3已经改为通过状态码判断200 204 成功
            int statusCode = response.getStatusLine().getStatusCode();
            //post方法有可能会没有返回值的情况
            String responseString;
            if (response.getEntity() == null) {
                responseString = null;
            } else {
                responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
            if (HttpStatus.SC_OK == statusCode || HttpStatus.SC_NO_CONTENT == statusCode) {
                this.log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}", url, requestStr, responseString);
                return responseString;
            } else {
                //有错误提示信息返回
                JsonObject jsonObject = GsonParser.parse(responseString);
                throw convertException(jsonObject);
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
            throw (e instanceof WxPayException) ? (WxPayException) e : new WxPayException(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
        }


    }

    @Override
    public String postV3WithWechatpaySerial(String url, String requestStr) throws WxPayException {
        CloseableHttpClient httpClient = this.createApiV3HttpClient();
        HttpPost httpPost = this.createHttpPost(url, requestStr);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json");
        String serialNumber = getWxPayConfig().getVerifier().getValidCertificate().getSerialNumber().toString(16).toUpperCase();
        httpPost.addHeader("Wechatpay-Serial", serialNumber);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            //v3已经改为通过状态码判断200 204 成功
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString = "{}";
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }

            if (HttpStatus.SC_OK == statusCode || HttpStatus.SC_NO_CONTENT == statusCode) {
                this.log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}", url, requestStr, responseString);
                return responseString;
            } else {
                //有错误提示信息返回
                JsonObject jsonObject = GsonParser.parse(responseString);
                throw convertException(jsonObject);
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
            e.printStackTrace();
            throw (e instanceof WxPayException) ? (WxPayException) e : new WxPayException(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public String postV3(String url, HttpPost httpPost) throws WxPayException {
        return this.requestV3(url, httpPost);
    }

    @Override
    public String requestV3(String url, HttpRequestBase httpRequest) throws WxPayException {
        httpRequest.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(this.getWxPayConfig().getHttpConnectionTimeout())
                .setConnectTimeout(this.getWxPayConfig().getHttpConnectionTimeout())
                .setSocketTimeout(this.getWxPayConfig().getHttpTimeout())
                .build());

        CloseableHttpClient httpClient = this.createApiV3HttpClient();
        try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
            //v3已经改为通过状态码判断200 204 成功
            int statusCode = response.getStatusLine().getStatusCode();
            //post方法有可能会没有返回值的情况
            String responseString;
            if (response.getEntity() == null) {
                responseString = null;
            } else {
                responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
            if (HttpStatus.SC_OK == statusCode || HttpStatus.SC_NO_CONTENT == statusCode) {
                this.log.info("\n【请求地址】：{}\n【响应数据】：{}", url, responseString);
                return responseString;
            } else {
                //有错误提示信息返回
                JsonObject jsonObject = GsonParser.parse(responseString);
                throw convertException(jsonObject);
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【异常信息】：{}", url, e.getMessage());
            throw (e instanceof WxPayException) ? (WxPayException) e : new WxPayException(e.getMessage(), e);
        } finally {
            httpRequest.releaseConnection();
        }
    }

    @Override
    public String getV3(String url) throws WxPayException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("Content-Type", "application/json");
        return this.requestV3(url.toString(), httpGet);
    }

    @Override
    public InputStream downloadV3(String url) throws WxPayException {
        CloseableHttpClient httpClient = this.createApiV3HttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", ContentType.WILDCARD.getMimeType());
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            //v3已经改为通过状态码判断200 204 成功
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode || HttpStatus.SC_NO_CONTENT == statusCode) {
                this.log.info("\n【请求地址】：{}\n", url);
                return response.getEntity().getContent();
            } else {
                //有错误提示信息返回
                String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JsonObject jsonObject = GsonParser.parse(responseString);
                throw convertException(jsonObject);
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【异常信息】：{}", url, e.getMessage());
            throw (e instanceof WxPayException) ? (WxPayException) e : new WxPayException(e.getMessage(), e);
        } finally {
            httpGet.releaseConnection();
        }
    }

    private CloseableHttpClient createApiV3HttpClient() throws WxPayException {
        CloseableHttpClient apiV3HttpClient = this.getWxPayConfig().getApiV3HttpClient();
        if (null == apiV3HttpClient) {
            return this.getWxPayConfig().initApiV3HttpClient();
        }
        return apiV3HttpClient;
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

    private WxPayException convertException(JsonObject jsonObject) {
        //todo 这里考虑使用新的适用于V3的异常
        JsonElement codeElement = jsonObject.get("code");
        String code = codeElement == null ? null : codeElement.getAsString();
        String message = jsonObject.get("message").getAsString();
        WxPayException wxPayException = new WxPayException(message);
        wxPayException.setErrCode(code);
        wxPayException.setErrCodeDes(message);
        return wxPayException;
    }
}
