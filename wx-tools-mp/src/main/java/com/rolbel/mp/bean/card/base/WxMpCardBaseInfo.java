package com.rolbel.mp.bean.card.base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;

public class WxMpCardBaseInfo {
    private JSONObject m_data;

    public WxMpCardBaseInfo() {
        m_data = new JSONObject();
        m_data.put("date_info", new JSONObject());
        m_data.put("location_id_list", new JSONArray());
        m_data.put("sku", new JSONObject());
    }

    public JSONObject getData() {
        return this.m_data;
    }

    public String toJson() {
        return m_data.toString();
    }

    /**
     * 卡券的商户logo，建议像素为300*300
     */
    public void setLogoUrl(String logoUrl) {
        m_data.put("logo_url", logoUrl);
    }

    public String getLogoUrl() {
        return m_data.optString("logo_url");
    }

    static int CODE_TYPE_TEXT = 0;
    static int CODE_TYPE_BARCODE = 1;
    static int CODE_TYPE_QRCODE = 2;

    /**
     * 码型
     */
    public void setCodeType(int code) {
        m_data.put("code_type", code);
    }

    public int getCodeType() {
        return m_data.optInt("code_type");
    }

    /**
     * 商户名字,字数上限为12个汉字
     */
    public void setBrandName(String name) {
        m_data.put("brand_name", name);
    }

    public String getBrandName() {
        return m_data.optString("brand_name");
    }

    /**
     * 卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)
     */
    public void setTitle(String title) {
        m_data.put("title", title);
    }

    public String getTitle() {
        return m_data.optString("title");
    }

    /**
     * 券颜色。按色彩规范标注填写Color010-Color100
     */
    public void setColor(String color) {
        m_data.put("color", color);
    }

    public String getColor() {
        return m_data.optString("color");
    }

    /**
     * 卡券使用提醒，字数上限为16个汉字
     */
    public void setNotice(String notice) {
        m_data.put("notice", notice);
    }

    public String getNotice() {
        return m_data.optString("notice");
    }

    public void setDescription(String desc) {
        m_data.put("description", desc);
    }

    public String getDescription() {
        return m_data.optString("description");
    }

    public void setQuantity(int quantity) {
        m_data.optJSONObject("sku").put("quantity", quantity);
    }

    public int getQuantity() {
        return m_data.optJSONObject("sku").optInt("quantity");
    }

    public org.json.JSONObject getDateInfo() {
        return m_data.optJSONObject("date_info");
    }

    public void setDateInfoTimeRange(Date beginTime, Date endTime) {
        setDateInfoTimeRange(beginTime.getTime() / 1000, endTime.getTime() / 1000);
    }

    public void setDateInfoTimeRange(long beginTimestamp, long endTimestamp) {
        getDateInfo().put("type", 1);
        getDateInfo().put("begin_timestamp", beginTimestamp);
        getDateInfo().put("end_timestamp", endTimestamp);
    }

    public void setDateInfoFixTerm(int fixedTerm) {
        setDateInfoFixTerm(fixedTerm, 0);
    }

    public void setDateInfoFixTerm(int fixedTerm, int fixedBeginTerm) //fixedBeginTerm是领取后多少天开始生效
    {
        getDateInfo().put("type", 2);
        getDateInfo().put("fixed_term", fixedTerm);
        getDateInfo().put("fixed_begin_term", fixedBeginTerm);
    }

    public void setUseCustomCode(boolean isUse) {
        m_data.put("use_custom_code", isUse);
    }

    public boolean getUseCustomCode() {
        return m_data.optBoolean("use_custom_code");
    }

    public void setBindOpenid(boolean isBind) {
        m_data.put("bind_openid", isBind);
    }

    public boolean getBindOpenid() {
        return m_data.optBoolean("bind_openid");
    }

    public void setServicePhone(String phone) {
        m_data.put("service_phone", phone);
    }

    public String getServicePhone() {
        return m_data.optString("service_phone");
    }

    public void setLocationIdList(Collection<Integer> value) {
        org.json.JSONArray array = new org.json.JSONArray();
        value.forEach(array::put);
        m_data.put("location_id_list", array);
    }

    public void addLocationIdList(int locationId) {
        getLocationIdList().put(locationId);
    }

    public org.json.JSONArray getLocationIdList() {
        return m_data.getJSONArray("location_id_list");
    }

    public void setCenterTitle(String centerTitle) {
        m_data.put("center_title", centerTitle);
    }

    public String getCenterTitle() {
        return m_data.optString("center_title");
    }

    public void setCenterSubTitle(String centerSubTitle) {
        m_data.put("center_sub_title", centerSubTitle);
    }

    public String getCenterSubTitle() {
        return m_data.optString("center_sub_title");
    }

    public void setCenterUrl(String centerUrl) {
        m_data.put("center_url", centerUrl);
    }

    public String getCenterUrl() {
        return m_data.optString("center_url");
    }

    public void setCenterAppBrandUserName(String centerAppBrandUserName) {
        m_data.put("center_app_brand_user_name", centerAppBrandUserName);
    }

    public String getCenterAppBrandUserName() {
        return m_data.optString("center_app_brand_user_name");
    }

    public void setCenterAppBrandPass(String centerAppBrandPass) {
        m_data.put("center_app_brand_pass", centerAppBrandPass);
    }

    public String getCenterAppBrandPass() {
        return m_data.optString("center_app_brand_pass");
    }

    public void setCustomUrlName(String customUrlName) {
        m_data.put("custom_url_name", customUrlName);
    }

    public String getCustomUrlName() {
        return m_data.optString("custom_url_name");
    }

    public void setCustomUrl(String customUrl) {
        m_data.put("custom_url", customUrl);
    }

    public String getCustomUrl() {
        return m_data.optString("custom_url");
    }

    public void setCustomUrlSubTitle(String customUrlSubTitle) {
        m_data.put("custom_url_sub_title", customUrlSubTitle);
    }

    public String getCustomUrlSubTitle() {
        return m_data.optString("custom_url_sub_title");
    }

    public void setCustomAppBrandUserName(String customAppBrandUserName) {
        m_data.put("custom_app_brand_user_name", customAppBrandUserName);
    }

    public String getCustomAppBrandUserName() {
        return m_data.optString("custom_app_brand_user_name");
    }

    public void setCustomAppBrandPass(String customAppBrandPass) {
        m_data.put("custom_app_brand_pass", customAppBrandPass);
    }

    public String getCustomAppBrandPass() {
        return m_data.optString("custom_app_brand_pass");
    }

    public void setPromotionUrlName(String promotionUrlName) {
        m_data.put("promotion_url_name", promotionUrlName);
    }

    public String getPromotionUrlName() {
        return m_data.optString("promotion_url_name");
    }

    public void setPromotionUrl(String promotionUrl) {
        m_data.put("promotion_url", promotionUrl);
    }

    public String getPromotionUrl() {
        return m_data.optString("promotion_url");
    }

    public void setPromotionUrlSubTitle(String promotionUrlSubTitle) {
        m_data.put("promotion_url_sub_title", promotionUrlSubTitle);
    }

    public String getPromotionUrlSubTitle() {
        return m_data.optString("promotion_url_sub_title");
    }

    public void setPromotionAppBrandUserName(String promotionAppBrandUserName) {
        m_data.put("promotion_app_brand_user_name", promotionAppBrandUserName);
    }

    public String getPromotionAppBrandUserName() {
        return m_data.optString("promotion_app_brand_user_name");
    }

    public void setPromotionAppBrandPass(String promotionAppBrandUserName) {
        m_data.put("promotion_app_brand_pass", promotionAppBrandUserName);
    }

    public String getPromotionAppBrandPass() {
        return m_data.optString("promotion_app_brand_pass");
    }

    public void setGetLimit(Integer limit) {
        m_data.put("get_limit", limit);
    }

    public Integer getGetLimit() {
        return m_data.optInt("get_limit");
    }

    public void setUseLimit(Integer limit) {
        m_data.put("use_limit", limit);
    }

    public Integer getUseLimit() {
        return m_data.optInt("use_limit");
    }

    public void setCanShare(Boolean isUse) {
        m_data.put("can_share", isUse);
    }

    public Boolean getCanShare() {
        return m_data.optBoolean("can_share");
    }

    public void setCanGiveFriend(Boolean isUse) {
        m_data.put("can_give_friend", isUse);
    }

    public Boolean getCanGiveFriend() {
        return m_data.optBoolean("can_give_friend");
    }
}
