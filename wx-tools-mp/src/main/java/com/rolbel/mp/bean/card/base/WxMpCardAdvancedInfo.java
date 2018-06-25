package com.rolbel.mp.bean.card.base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class WxMpCardAdvancedInfo {
    private JSONObject m_data;

    public WxMpCardAdvancedInfo() {
        m_data = new JSONObject();
        m_data.put("text_image_list", new JSONArray());
        m_data.put("time_limit", new JSONArray());
        m_data.put("business_service", new JSONArray());
    }

    public JSONObject getData() {
        return this.m_data;
    }

    public void setUseCondition(String acceptCategory, String rejectCategory, boolean canUseWithOtherDiscount) {
        JSONObject useCondition = new JSONObject();
        useCondition.put("accept_category", acceptCategory);
        useCondition.put("reject_category", rejectCategory);
        useCondition.put("can_use_with_other_discount", canUseWithOtherDiscount);

        m_data.put("use_condition", useCondition);
    }

    public JSONObject getUseCondition() {
        return this.m_data.getJSONObject("use_condition");
    }

    public void setAbstract(String abstractDesc, List<String> iconUrls) {
        JSONObject abstractJson = new JSONObject();
        abstractJson.put("abstract", abstractDesc);

        JSONArray array = new org.json.JSONArray();
        iconUrls.forEach(array::put);
        abstractJson.put("icon_url_list", array);

        this.m_data.put("abstract", abstractJson);
    }

    public JSONObject getAbstract() {
        return this.m_data.getJSONObject("abstract");
    }

    public void addTextImage(String text, String imageUrl) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", text);
        jsonObject.put("image_url", imageUrl);

        this.m_data.optJSONArray("text_image_list").put(jsonObject);
    }

    public JSONArray getTextImageList() {
        return this.m_data.getJSONArray("text_image_list");
    }

    public void addTimeLimit(TimeLimitEnum timeLimitEnum, Integer beginHour, Integer beginMinute, Integer endHour, Integer endMinute) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", timeLimitEnum);
        jsonObject.put("begin_hour", beginHour);
        jsonObject.put("end_hour", endHour);
        jsonObject.put("beginMinute", beginMinute);
        jsonObject.put("end_minute", endMinute);

        this.m_data.optJSONArray("time_limit").put(jsonObject);
    }

    public JSONArray getTimeLimit() {
        return this.m_data.optJSONArray("time_limit");
    }

    public void addBusinessService(BusinessServiceEnum businessServiceEnum) {
        this.m_data.optJSONArray("business_service").put(businessServiceEnum);
    }

    public JSONArray getBusinessService() {
        return this.m_data.getJSONArray("business_service");
    }

    public enum TimeLimitEnum {
        HOLIDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;

        public String toString() {
            return this.name();
        }
    }

    public enum BusinessServiceEnum {
        BIZ_SERVICE_DELIVER,
        BIZ_SERVICE_FREE_PARK,
        BIZ_SERVICE_WITH_PET,
        BIZ_SERVICE_FREE_WIFI;

        public String toString() {
            return this.name();
        }
    }
}
