package com.naspat.ma.bean.analysis;

import com.naspat.ma.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户画像
 */
@Data
public class WxMaUserPortrait implements Serializable {
    private static final long serialVersionUID = 7012827360686388437L;

    /**
     * 时间范围,如： "20170611-20170617"
     */
    private String refDate;
    /**
     * 新用户
     */
    private Item visitUvNew;
    /**
     * 活跃用户
     */
    private Item visitUv;

    public static WxMaUserPortrait fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaUserPortrait.class);
    }

    @Data
    public static class Item {
        /**
         * key: 省份，如北京、广东等
         * value: 活跃用户数或新用户数
         */
        private Map<String, Long> province;
        /**
         * key: 城市，如北京、广州等
         * value: 活跃用户数或新用户数
         */
        private Map<String, Long> city;
        /**
         * key: 性别，包括男、女、未知
         * value: 活跃用户数或新用户数
         */
        private Map<String, Long> genders;
        /**
         * key: 终端类型，包括iPhone, android,其他
         * value: 活跃用户数或新用户数
         */
        private Map<String, Long> platforms;
        /**
         * key: 机型，如苹果iPhone6, OPPO R9等
         * value: 活跃用户数或新用户数
         */
        private Map<String, Long> devices;
        /**
         * key: 年龄，包括17岁以下、18-24岁等区间
         * value: 活跃用户数或新用户数
         */
        private Map<String, Long> ages;
    }
}
