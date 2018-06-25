package com.rolbel.mp.bean.card.request;

public class WxMpCardGroupon extends WxMpCard {
    public WxMpCardGroupon() {
        init("GROUPON");
    }

    public void setDealDetail(String detail) {
        m_data.put("deal_detail", detail);
    }
}
