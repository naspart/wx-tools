package com.rolbel.mp.bean.card.request;

public class WxMpCardDiscount extends WxMpCard {
    public WxMpCardDiscount() {
        init("DISCOUNT");
    }

    public void setDiscount(Integer discount) {
        m_data.put("discount", discount);
    }
}
