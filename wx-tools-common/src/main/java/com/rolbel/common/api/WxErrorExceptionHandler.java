package com.rolbel.common.api;

import com.rolbel.common.exception.WxErrorException;

/**
 * WxErrorException处理器.
 */
public interface WxErrorExceptionHandler {

    void handle(WxErrorException e);
}
