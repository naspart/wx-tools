package com.naspat.open.api.impl;

import com.naspat.pay.api.impl.WxPayServiceImpl;
import com.naspat.pay.config.WxPayConfig;

public class WxOpenPayServiceImpl extends WxPayServiceImpl {
    WxOpenPayServiceImpl(WxPayConfig wxPayConfig) {
        this.setWxPayConfig(wxPayConfig);
    }
}
