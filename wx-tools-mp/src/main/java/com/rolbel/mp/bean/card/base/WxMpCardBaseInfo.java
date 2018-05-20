package com.rolbel.mp.bean.card.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.rolbel.common.annotation.Required;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class WxMpCardBaseInfo implements Serializable {
    private static final long serialVersionUID = -5345805620475047226L;

    /**
     * 卡券的商户logo，建议像素为300*300
     */
    @Required
    @SerializedName("logo_url")
    private String logoUrl;

    /**
     * 商户名字,字数上限为12个汉字
     */
    @Required
    @SerializedName("brand_name")
    private String brandName;

    /**
     * 码型：
     * <p>
     * CODE_TYPE_TEXT         -> 文 本；
     * CODE_TYPE_BARCODE      -> 一维码
     * CODE_TYPE_QRCODE       -> 二维码
     * CODE_TYPE_ONLY_QRCODE  -> 二维码无code显示；
     * CODE_TYPE_ONLY_BARCODE -> 一维码无code显示；
     * CODE_TYPE_NONE         -> 不显示code和条形码类型
     */
    @Required
    @SerializedName("code_type")
    private String codeType;

    /**
     * 卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)
     */
    @Required
    @SerializedName("title")
    private String title;

    /**
     * 券颜色。按色彩规范标注填写Color010-Color100
     */
    @Required
    @SerializedName("color")
    private String color;

    /**
     * 卡券使用提醒，字数上限为16个汉字
     */
    @Required
    @SerializedName("notice")
    private String notice;

    /**
     * 客服电话。
     */
    @SerializedName("service_phone")
    private String servicePhone;

    /**
     * 卡券使用说明，字数上限为1024个汉字
     */
    @Required
    @SerializedName("description")
    private String description;

    /**
     * 使用日期，有效期的信息。
     */
    @SerializedName("date_info")
    private WxMpCardDateInfo dateInfo;

    /**
     * 商品信息
     */
    @Required
    @SerializedName("sku")
    private WxMpCardSku sku;

    /**
     * 是否自定义Code码 。填写true或false，默认为false。 通常自有优惠码系统的开发者选择 自定义Code码，并在卡券投放时带入 Code码，详情见 是否自定义Code码 。
     */
    @SerializedName("use_custom_code")
    private Boolean useCustomCode;

    /**
     * 填入 GET_CUSTOM_CODE_MODE_DEPOSIT 表示该卡券为预存code模式卡券， 须导入超过库存数目的自定义code后方可投放， 填入该字段后，quantity字段须为0,须导入code 后再增加库存
     */
    @SerializedName("get_custom_code_mode")
    private String getCustomCodeMode;

    /**
     * 是否指定用户领取，填写true或false 。默认为false。通常指定特殊用户群体 投放卡券或防止刷券时选择指定用户领取。
     */
    @SerializedName("bind_openid")
    private Boolean bindOpenid;

    /**
     * 门店位置poiid。 调用 POI门店管理接 口 获取门店位置poiid。具备线下门店 的商户为必填。
     */
    @SerializedName("location_id_list")
    private List<Long> locationIdList;

    /**
     * 设置本卡券支持全部门店，与location_id_list互斥
     */
    @SerializedName("use_all_locations")
    private Boolean useAllLocations;

    /**
     * 卡券顶部居中的按钮，仅在卡券状 态正常(可以核销)时显示
     */
    @SerializedName("center_title")
    private String centerTitle;

    /**
     * 显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示。
     */
    @SerializedName("center_sub_title")
    private String centerSubTitle;

    /**
     * 顶部居中的url ，仅在卡券状态正常(可以核销)时显示。
     */
    @SerializedName("center_url")
    private String centerUrl;

    /**
     * 卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序 。
     */
    @SerializedName("center_app_brand_user_name")
    private String centerAppBrandUserName;

    /**
     * 卡券跳转的小程序的path
     */
    @SerializedName("center_app_brand_pass")
    private String centerAppBrandPass;

    /**
     * 自定义跳转外链的入口名字。
     */
    @SerializedName("custom_url_name")
    private String customUrlName;

    /**
     * 自定义跳转的URL。
     */
    @SerializedName("custom_url")
    private String customUrl;

    /**
     * 显示在入口右侧的提示语。
     */
    @SerializedName("custom_url_sub_title")
    private String customUrlSubTitle;

    /**
     * 卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序 。
     */
    @SerializedName("custom_app_brand_user_name")
    private String customAppBrandUserName;

    /**
     * 卡券跳转的小程序的path
     */
    @SerializedName("custom_app_brand_pass")
    private String customAppBrandPass;

    /**
     * 营销场景的自定义入口名称。
     */
    @SerializedName("promotion_url_name")
    private String promotionUrlName;

    /**
     * 入口跳转外链的地址链接。
     */
    @SerializedName("promotion_url")
    private String promotionUrl;

    /**
     * 显示在营销入口右侧的提示语。
     */
    @SerializedName("promotion_url_sub_title")
    private String promotionUrlSubTitle;

    /**
     * 卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序 。
     */
    @SerializedName("promotion_app_brand_user_name")
    private String promotionAppBrandUserName;

    /**
     * 卡券跳转的小程序的path
     */
    @SerializedName("promotion_app_brand_pass")
    private String promotionAppBrandPass;

    /**
     * 每人可领券的数量限制,不填写默认为50。
     */
    @SerializedName("get_limit")
    private Integer getLimit;

    /**
     * 每人可核销的数量限制,不填写默认为50。
     */
    @SerializedName("use_limit")
    private Integer useLimit;

    /**
     * 卡券领取页面是否可分享。
     */
    @SerializedName("can_share")
    private Boolean canShare;

    /**
     * 卡券是否可转赠。
     */
    @SerializedName("can_give_friend")
    private Boolean canGiveFriend;

    private String source;

    public String toJson() {
        JsonElement baseInfo = WxMpGsonBuilder.create().toJsonTree(this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("base_info", baseInfo);

        return jsonObject.toString();
    }
}
