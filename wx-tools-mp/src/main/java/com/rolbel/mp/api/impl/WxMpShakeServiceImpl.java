package com.rolbel.mp.api.impl;

import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.bean.result.WxError;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.api.WxMpShakeService;
import com.rolbel.mp.bean.shake.*;
import com.rolbel.mp.util.json.WxMpGsonBuilder;

public class WxMpShakeServiceImpl implements WxMpShakeService {
    private WxMpService wxMpService;

    public WxMpShakeServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public void registerShake(WxMpShakeRegisterRequest wxMpShakeRegisterRequest) throws WxErrorException {
        this.wxMpService.post(SHAKE_ACCOUNT_REGISTER_URL, wxMpShakeRegisterRequest.toJson());
    }

    /**
     * <pre>
     *     查询摇一摇开通审核状态
     * </pre>
     *
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpShakeRegisterQueryResult registerStatusQuery() throws WxErrorException {
        String response = this.wxMpService.post(SHAKE_ACCOUNT_REGISTER_QUERY_URL, null);

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(response).getAsJsonObject().get("data"),
                new TypeToken<WxMpShakeRegisterQueryResult>() {
                }.getType());
    }

    @Override
    public WxMpShakeDeviceApplyResult applyDevice(WxMpShakeDeviceApplyRequest wxMpShakeDeviceApplyRequest) throws WxErrorException {
        String response = this.wxMpService.post(SHAKE_DEVICE_APPLY_URL, wxMpShakeDeviceApplyRequest.toJson());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(response).getAsJsonObject().get("data"),
                new TypeToken<WxMpShakeDeviceApplyResult>() {
                }.getType());
    }

    /**
     * <pre>
     * 获取设备及用户信息<br/>
     * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息。
     * 详情请见: https://mp.weixin.qq.com/wiki?action=doc&id=mp1443447963
     * http请求方式: POST（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/shakearound/user/getshakeinfo?access_token=ACCESS_TOKE
     * </pre>
     *
     * @param wxMpShakeQuery 查询参数
     */
    @Override
    public WxMpShakeInfoResult getShakeInfo(WxMpShakeQuery wxMpShakeQuery) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_SHAKE_INFO_URL, wxMpShakeQuery.toJson());

        return WxMpShakeInfoResult.fromJson(responseContent);
    }

    @Override
    public WxMpShakeAroundPageAddResult pageAdd(WxMpShakeAroundPageAddQuery shakeAroundPageAddQuery) throws WxErrorException {
        String responseContent = this.wxMpService.post(SHAKE_ADD_PAGE_URL, shakeAroundPageAddQuery.toJson());

        return WxMpShakeAroundPageAddResult.fromJson(responseContent);
    }

    @Override
    public WxError deviceBindPageQuery(WxMpShakeAroundDeviceBindPageQuery shakeAroundDeviceBindPageQuery) throws WxErrorException {
        String responseContent = this.wxMpService.post(SHAKE_DEVICE_BIND_PAGE_URL, shakeAroundDeviceBindPageQuery.toJson());

        return WxError.fromJson(responseContent);
    }

    @Override
    public WxMpShakeAroundRelationSearchResult relationSearch(WxMpShakeAroundRelationSearchQuery shakeAroundRelationSearchQuery) throws WxErrorException {
        String url = "https://api.weixin.qq.com/shakearound/relation/search";
        String responseContent = this.wxMpService.post(url, shakeAroundRelationSearchQuery.toJson());

        return WxMpShakeAroundRelationSearchResult.fromJson(responseContent);
    }
}
