package com.naspat.mp.api;

import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.mp.bean.shake_around.*;

/**
 * 摇一摇周边的相关接口
 */
public interface WxMpShakeService {
    String SHAKE_ACCOUNT_REGISTER_URL = "https://api.weixin.qq.com/shakearound/account/register";
    String SHAKE_ACCOUNT_REGISTER_QUERY_URL = "https://api.weixin.qq.com/shakearound/account/auditstatus";
    String GET_SHAKE_INFO_URL = "https://api.weixin.qq.com/shakearound/user/getshakeinfo";
    String SHAKE_ADD_PAGE_URL = "https://api.weixin.qq.com/shakearound/page/add";
    String SHAKE_DEVICE_BIND_PAGE_URL = "https://api.weixin.qq.com/shakearound/device/bindpage";
    String SHAKE_DEVICE_APPLY_URL = "https://api.weixin.qq.com/shakearound/device/applyid";

    void registerShake(WxMpShakeAroundRegisterRequest wxMpShakeAroundRegisterRequest) throws WxErrorException;

    WxMpShakeAroundRegisterQueryResult registerStatusQuery() throws WxErrorException;

    WxMpShakeAroundDeviceApplyResult applyDevice(WxMpShakeAroundDeviceApplyRequest wxMpShakeAroundDeviceApplyRequest) throws WxErrorException;

    /**
     * <pre>
     * 获取设备及用户信息<br/>
     * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息。
     * 详情请见: https://mp.weixin.qq.com/wiki?action=doc&id=mp1443447963
     * http请求方式: POST（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/shakearound/user/getshakeinfo?access_token=ACCESS_TOKE
     * </pre>
     *
     * @param wxMpShakeAroundQuery 查询参数
     */
    WxMpShakeAroundInfoResult getShakeInfo(WxMpShakeAroundQuery wxMpShakeAroundQuery) throws WxErrorException;

    /**
     * <pre>
     * 页面管理<br/>
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459246752
     * </pre>
     *
     * @param shakeAroundPageAddQuery
     * @return
     * @throws WxErrorException
     */
    WxMpShakeAroundPageAddResult pageAdd(WxMpShakeAroundPageAddQuery shakeAroundPageAddQuery) throws WxErrorException;

    /**
     * <pre>
     * 配置设备与页面的关联关系<br/>
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459301931
     * </pre>
     *
     * @param shakeAroundDeviceBindPageQuery
     * @return
     * @throws WxErrorException
     */
    WxError deviceBindPageQuery(WxMpShakeAroundDeviceBindPageQuery shakeAroundDeviceBindPageQuery) throws WxErrorException;

    /**
     * <pre>
     * 查询设备与页面的关联关系<br/>
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443447914
     * </pre>
     *
     * @param shakeAroundRelationSearchQuery
     * @return
     * @throws WxErrorException
     */
    WxMpShakeAroundRelationSearchResult relationSearch(WxMpShakeAroundRelationSearchQuery shakeAroundRelationSearchQuery) throws WxErrorException;
}
