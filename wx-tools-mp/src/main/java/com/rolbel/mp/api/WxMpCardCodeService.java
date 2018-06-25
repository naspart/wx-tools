package com.rolbel.mp.api;

import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.bean.card.request.WxMpImportCardCode;
import com.rolbel.mp.bean.card.result.WxMpCheckCodeResult;
import com.rolbel.mp.bean.result.WxMpCardResult;

public interface WxMpCardCodeService {
    String CARD_CODE_DEPOSIT_URL = "http://api.weixin.qq.com/card/code/deposit";
    String CARD_CODE_GET_DEPOSIT_COUNT_URL = "http://api.weixin.qq.com/card/code/getdepositcount";
    String CARD_CODE_CHECK_URL = "http://api.weixin.qq.com/card/code/checkcode";
    String CARD_CODE_DECRYPT_URL = "https://api.weixin.qq.com/card/code/decrypt";
    String CARD_CODE_GET_URL = "https://api.weixin.qq.com/card/code/get";
    String CARD_CODE_CONSUME_URL = "https://api.weixin.qq.com/card/code/consume";
    String CARD_CODE_MARK_URL = "https://api.weixin.qq.com/card/code/mark";

    WxMpService getWxMpService();

    WxMpCardResult queryCardCode(String cardId, String code, boolean checkConsume) throws WxErrorException;

    String consumeCardCode(String code) throws WxErrorException;

    String consumeCardCode(String code, String cardId) throws WxErrorException;

    String decryptCardCode(String encryptCode) throws WxErrorException;

    void importCardCode(WxMpImportCardCode request) throws WxErrorException;

    Integer getImportCardCodeCount(String cardId) throws WxErrorException;

    WxMpCheckCodeResult checkCode(WxMpImportCardCode request) throws WxErrorException;

    void markCardCode(String code, String cardId, String openId, boolean isMark) throws WxErrorException;
}
