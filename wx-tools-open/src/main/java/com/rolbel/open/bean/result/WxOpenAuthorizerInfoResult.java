package com.rolbel.open.bean.result;

import lombok.Data;
import com.rolbel.open.bean.auth.WxOpenAuthorizationInfo;
import com.rolbel.open.bean.auth.WxOpenAuthorizerInfo;

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
