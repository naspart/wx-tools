package com.rolbel.mp.bean.shake_around;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 摇一摇周边：获取设备及用户信息接口返回JSON数据接收类
 */
@Data
public class WxMpShakeAroundInfoResult implements Serializable {
    private static final long serialVersionUID = -1604561297395395468L;

    private int errCode;

    private String errMsg;

    private ShakeInfoData data;

    public static WxMpShakeAroundInfoResult fromJson(String json) {
        return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpShakeAroundInfoResult.class);
    }

    @Data
    public class ShakeInfoData implements Serializable {
        private static final long serialVersionUID = -4828142206067489488L;

        private String page_id;

        private String openid;

        private String poi_id;

        private String brand_userame;

        private BeaconInfo beacon_info;

        @Data
        public class BeaconInfo implements Serializable {
            private static final long serialVersionUID = -8995733049982933362L;

            private double distance;

            private Integer major;

            private Integer measure_power;

            private Integer minor;

            private Integer rssi;

            private String uuid;
        }
    }
}