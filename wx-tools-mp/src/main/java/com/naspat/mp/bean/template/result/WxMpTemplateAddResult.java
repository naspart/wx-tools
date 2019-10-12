package com.naspat.mp.bean.template.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMpTemplateAddResult implements Serializable {
    private static final long serialVersionUID = 1501960708786261070L;

    @SerializedName(value = "template_id")
    private String templateId;
}
