package com.rolbel.common.util.http;


import com.rolbel.common.error.WxErrorException;

import java.io.IOException;

/**
 * 接口 {@code RequestExecutor} http请求执行器.
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 */
public interface RequestExecutor<T, E> {

    /**
     * 执行http请求.
     *
     * @param uri  uri
     * @param data 数据
     * @return http执行结果
     * @throws WxErrorException 微信异常
     * @throws IOException      io异常
     */
    T execute(String uri, E data) throws WxErrorException, IOException;

    /**
     * 执行http请求.
     *
     * @param uri     uri
     * @param data    数据
     * @param handler http响应处理器
     * @throws WxErrorException 微信异常
     * @throws IOException      io异常
     */
    void execute(String uri, E data, ResponseHandler<T> handler) throws WxErrorException, IOException;
}
