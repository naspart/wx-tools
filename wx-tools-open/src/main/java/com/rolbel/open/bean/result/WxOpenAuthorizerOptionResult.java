package com.rolbel.open.bean.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxOpenAuthorizerOptionResult implements Serializable {
    private static final long serialVersionUID = 7828631388652213745L;

    String authorizerAppid;
    String optionName;
    String optionValue;
}
