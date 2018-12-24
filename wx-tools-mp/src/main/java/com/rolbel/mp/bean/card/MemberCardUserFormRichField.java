package com.rolbel.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import com.rolbel.mp.bean.card.enums.CardRichFieldType;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 富文本字段.
 */
@Data
public class MemberCardUserFormRichField {
    /**
     * 富文本类型
     */
    @SerializedName("type")
    private CardRichFieldType type;

    @SerializedName("name")
    private String name;

    @SerializedName("values")
    private List<String> valueList;

    public void add(String value) {
        if (valueList == null) {
            valueList = new ArrayList<String>();
        }
        valueList.add(value);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
