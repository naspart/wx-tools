package com.rolbel.mp.bean.template.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpTemplateSendMsgResult implements Serializable {
    private static final long serialVersionUID = 3664966911493915063L;

    @SerializedName(value = "msgid")
    private String msgId;
}
