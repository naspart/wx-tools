package com.rolbel.mp.bean.card.request;

public class WxMpCardGift extends WxMpCard {
    public WxMpCardGift() {
        init("GIFT");
    }

    public void setDiscount(String gift) {
        m_data.put("gift", gift);
    }
}
