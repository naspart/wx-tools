package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpCheckCodeResult implements Serializable {
    private static final long serialVersionUID = 6451236723637814882L;

    @SerializedName(value = "exist_code")
    private List<String> existCode;

    @SerializedName(value = "not_exist_code")
    private List<String> notExistCode;
}
