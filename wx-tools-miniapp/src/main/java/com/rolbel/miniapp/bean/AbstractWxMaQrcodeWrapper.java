package com.rolbel.miniapp.bean;

import com.rolbel.miniapp.util.json.WxMaGsonBuilder;

/**
 * 微信二维码（小程序码）包装器.
 *
 * @author Element
 */
public abstract class AbstractWxMaQrcodeWrapper {

    @Override
    public String toString() {
        return WxMaGsonBuilder.create().toJson(this);
    }

}
