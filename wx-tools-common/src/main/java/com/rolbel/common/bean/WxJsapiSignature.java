package com.rolbel.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * jspai signature.
 */
@Data
public class WxJsapiSignature implements Serializable {
    private static final long serialVersionUID = -990765506843052776L;

    private String appId;

    private String nonceStr;

    private long timestamp;

    private String url;

    private String signature;
}
