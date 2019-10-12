package com.naspat.open.bean.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxOpenAuthorizationInfo implements Serializable {
    private static final long serialVersionUID = 5702750883845830969L;

    private String authorizerAppid;
    private String authorizerAccessToken;
    private int expiresIn;
    private String authorizerRefreshToken;
    private List<Integer> funcInfo;
}
