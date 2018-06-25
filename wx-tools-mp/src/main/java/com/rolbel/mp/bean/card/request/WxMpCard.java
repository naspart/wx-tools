package com.rolbel.mp.bean.card.request;

import com.rolbel.mp.bean.card.base.WxMpCardAdvancedInfo;
import com.rolbel.mp.bean.card.base.WxMpCardBaseInfo;
import org.json.JSONObject;

public abstract class WxMpCard {
    protected WxMpCardBaseInfo m_baseInfo;
    protected WxMpCardAdvancedInfo m_advancedInfo;
    protected JSONObject m_requestData;
    protected JSONObject m_data;
    protected String m_cardType;

    public WxMpCard() {
        m_baseInfo = new WxMpCardBaseInfo();
        m_advancedInfo = new WxMpCardAdvancedInfo();
        m_requestData = new JSONObject();
    }

    public void init(String cardType) {
        m_cardType = cardType;
        JSONObject obj = new JSONObject();
        obj.put("card_type", m_cardType.toUpperCase());

        m_data = new org.json.JSONObject();
        m_data.put("base_info", m_baseInfo.getData());

        m_data = new org.json.JSONObject();
        m_data.put("advanced_info", m_advancedInfo.getData());

        obj.put(m_cardType.toLowerCase(), m_data);
        m_requestData.put("card", obj);
    }

    public JSONObject getData() {
        return this.m_requestData;
    }

    public WxMpCardBaseInfo getBaseInfo() {
        return this.m_baseInfo;
    }

    public WxMpCardAdvancedInfo getAdvancedInfo() {
        return this.m_advancedInfo;
    }

    public String toJson() {
        return m_requestData.toString();
    }
}
