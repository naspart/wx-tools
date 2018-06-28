package com.rolbel.open.bean.result;

import lombok.Data;
import com.rolbel.open.bean.auth.WxOpenAuthorizationInfo;

import java.io.Serializable;

@Data
public class WxOpenQueryAuthResult implements Serializable {
    private static final long serialVersionUID = -5386454555298722801L;

    private WxOpenAuthorizationInfo authorizationInfo;
}
