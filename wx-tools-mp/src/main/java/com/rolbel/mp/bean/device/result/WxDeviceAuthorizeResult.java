package com.rolbel.mp.bean.device.result;

import com.rolbel.common.util.json.WxGsonBuilder;
import com.rolbel.mp.bean.device.AbstractDeviceBean;
import com.rolbel.mp.bean.device.BaseResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceAuthorizeResult extends AbstractDeviceBean {
    private static final long serialVersionUID = -3635715387098325784L;

    private List<BaseResp> resp;

    public static WxDeviceAuthorizeResult fromJson(String response) {
        return WxGsonBuilder.create().fromJson(response, WxDeviceAuthorizeResult.class);
    }
}
