package com.rolbel.mp.bean.template;

import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 模板消息.
 *
 * 功能参考：<a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">微信公众平台 - 发送模板消息接口</a>
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxMpTemplateMessage implements Serializable {
    private static final long serialVersionUID = -4969762231882239160L;

    /**
     * 接收者openid.
     */
    private String toUser;

    /**
     * 模板ID.
     */
    private String templateId;

    /**
     * <pre>
     * 模板跳转链接.
     *
     * url和miniprogram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。
     * 开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
     * </pre>
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据.
     *
     * @see #url
     */
    private MiniProgram miniProgram;

    /**
     * 模板数据.
     */
    @Builder.Default
    private List<WxMpTemplateData> data = new ArrayList<>();

    public WxMpTemplateMessage addData(WxMpTemplateData datum) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(datum);

        return this;
    }

    public WxMpTemplateMessage addData(List<WxMpTemplateData> dataList) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.addAll(dataList);

        return this;
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniProgram implements Serializable {
        private static final long serialVersionUID = 6227556643935950598L;

        private String appid;
        private String pagePath;

        /**
         * 是否使用path，否则使用pagepath.
         * 加入此字段是基于微信官方接口变化多端的考虑
         */
        private boolean usePath = true;
    }
}
