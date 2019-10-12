package com.naspat.open.bean.result;

import lombok.Data;
import com.naspat.open.bean.auth.WxOpenAuthorizationInfo;
import com.naspat.open.bean.auth.WxOpenAuthorizerInfo;

import java.io.Serializable;

@Data
public class WxOpenAuthorizerInfoResult implements Serializable {
    private static final long serialVersionUID = -683189005364547959L;

    private WxOpenAuthorizationInfo authorizationInfo;
    private WxOpenAuthorizerInfo authorizerInfo;

    public boolean isMiniProgram() {
        return authorizerInfo != null && authorizerInfo.getMiniProgramInfo() != null;
    }
}
