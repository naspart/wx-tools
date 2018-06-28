package com.rolbel.mp.bean.card.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpCardCheckCodeResult implements Serializable {
    private static final long serialVersionUID = 2338838603529402089L;

    @SerializedName(value = "exist_code")
    private List<String> existCode;

    @SerializedName(value = "not_exist_code")
    private List<String> notExistCode;
}
