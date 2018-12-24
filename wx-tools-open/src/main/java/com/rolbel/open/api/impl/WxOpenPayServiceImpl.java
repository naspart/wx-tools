package com.rolbel.open.api.impl;

import com.rolbel.pay.api.impl.WxPayServiceImpl;
import com.rolbel.pay.config.WxPayConfig;

public class WxOpenPayServiceImpl extends WxPayServiceImpl {
    WxOpenPayServiceImpl(WxPayConfig wxPayConfig) {
        this.setWxPayConfig(wxPayConfig);
    }
}
