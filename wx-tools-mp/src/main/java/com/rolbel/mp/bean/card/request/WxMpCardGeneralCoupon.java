package com.rolbel.mp.bean.card.request;

public class WxMpCardGeneralCoupon extends WxMpCard {
    public WxMpCardGeneralCoupon() {
        init("GENERAL_COUPON");
    }

    public void setDefaultDetail(String gift) {
        m_data.put("default_detail", gift);
    }
}
