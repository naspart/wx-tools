package com.rolbel.mp.bean.card.request;

public class WxMpCardMember extends WxMpCard {
    public WxMpCardMember() {
        init("MEMBER_CARD");
    }

    public void setBackgroundPicUrl(String url) {
        m_data.put("background_pic_url", url);
    }
}
