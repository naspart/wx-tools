package com.rolbel.mp.bean.card.request;

public class WxMpCardCash extends WxMpCard {
    public WxMpCardCash() {
        init("GROUPON");
    }

    public void setLeastCost(Integer leastCost) {
        m_data.put("least_cost", leastCost);
    }

    public void setReduceCost(Integer reduceCost) {
        m_data.put("reduce_cost", reduceCost);
    }
}
