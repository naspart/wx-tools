package com.rolbel.mp.api;

import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.bean.card.base.WxMpCardCreateRequest;
import com.rolbel.mp.bean.card.result.WxMpCardCreateResult;

public interface WxMpCardCreateService {
    String CARD_CREATE_URL = "https://api.weixin.qq.com/card/create";

    WxMpService getWxMpService();

    WxMpCardCreateResult createCard(WxMpCardCreateRequest wxMpGrouponCard) throws WxErrorException;
}
