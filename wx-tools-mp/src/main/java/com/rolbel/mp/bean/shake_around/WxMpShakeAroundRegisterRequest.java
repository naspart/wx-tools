package com.rolbel.mp.bean.shake_around;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpShakeAroundRegisterRequest implements Serializable {
    private static final long serialVersionUID = -7416704713520443372L;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "phone_number")
    private String phoneNumber;

    @SerializedName(value = "email")
    private String email;

    @SerializedName(value = "industry_id")
    private String industryId;

    @SerializedName(value = "qualification_cert_urls")
    private List<String> qualificationCertUrls;

    @SerializedName(value = "apply_reason")
    private String applyReason;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
