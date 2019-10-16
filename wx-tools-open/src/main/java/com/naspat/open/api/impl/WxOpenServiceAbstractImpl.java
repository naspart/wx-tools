package com.naspat.open.api.impl;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.common.util.http.RequestExecutor;
import com.naspat.common.util.http.RequestHttp;
import com.naspat.open.api.WxOpenComponentService;
import com.naspat.open.api.WxOpenService;
import com.naspat.open.config.WxOpenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class WxOpenServiceAbstractImpl implements WxOpenService, RequestHttp {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private WxOpenComponentService wxOpenComponentService = new WxOpenComponentServiceImpl(this);
    private WxOpenConfig wxOpenConfig;

    @Override
    public WxOpenComponentService getWxOpenComponentService() {
        return wxOpenComponentService;
    }

    @Override
    public WxOpenConfig getWxOpenConfig() {
        return wxOpenConfig;
    }

    @Override
    public void setWxOpenConfig(WxOpenConfig wxOpenConfig) {
        this.wxOpenConfig = wxOpenConfig;
        this.initHttp();
    }

    /**
     * 初始化 RequestHttp
     */
    public abstract void initHttp();

    synchronized <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        try {
            T result = executor.execute(uri, data);
            this.log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uri, data, result);
            return result;
        } catch (WxErrorException e) {
            WxError error = e.getError();
            if (error.getErrorCode() != 0) {
                this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uri, data, error);
                throw new WxErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uri, data, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
