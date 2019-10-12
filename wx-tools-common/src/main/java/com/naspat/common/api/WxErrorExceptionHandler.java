package com.naspat.common.api;

import com.naspat.common.error.WxErrorException;

/**
 * WxErrorException处理器.
 */
public interface WxErrorExceptionHandler {

    void handle(WxErrorException e);
}
