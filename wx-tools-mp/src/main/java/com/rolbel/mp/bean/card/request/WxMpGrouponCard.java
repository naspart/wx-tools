package com.rolbel.mp.bean.card.request;

import com.google.gson.annotations.SerializedName;
import com.rolbel.common.exception.WxErrorException;
import com.rolbel.mp.bean.card.base.*;
import com.rolbel.mp.util.json.WxMpGsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxMpGrouponCard implements WxMpCardBaseRequest, Serializable {
    private static final long serialVersionUID = -4430220088722776276L;

    @SerializedName("card_type")
    private String cardType;

    @SerializedName("groupon")
    private WxMpGrouponCardDetail groupon;

    @Override
    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }

    @Data
    @Builder
    public class WxMpGrouponCardDetail implements Serializable {
        private static final long serialVersionUID = -1103433073416775068L;

        @SerializedName("base_info")
        private WxMpCardBaseInfo baseInfo;

        @SerializedName("advanced_info")
        private WxMpCardAdvancedInfo advancedInfo;

        @SerializedName("deal_detail")
        private String dealDetail;
    }

    public static void main(String[] args) {
        WxMpCardBaseInfo baseInfo = WxMpCardBaseInfo.builder()
                .logoUrl("http://mmbiz.qpic.cn/mmbiz_jpg/oCk1ww9OL3nUVVasicSYGxmVg7icVMrgKyXGaWJibr1Swb3Cr58TMumwCuny317KDnlvKSicQQECnl1JOwMleeQ1SQ/0")
                .brandName("微信餐厅")
                .codeType("CODE_TYPE_TEXT")
                .title("132元双人火锅套餐")
                .color("Color010")
                .notice("使用时向服务员出示此券")
                .servicePhone("020-88888888")
                .description("不可与其他优惠同享\\n如需团购券发票，请在消费时向商户提出\\n店内均可使用，仅限堂食")
                .dateInfo(WxMpCardDateInfo.builder()
                        .type("DATE_TYPE_FIX_TERM")
                        .fixedTerm(15)
                        .fixedBeginTerm(0)
                        .build())
                .sku(WxMpCardSku.builder()
                        .quantity(500000)
                        .build())
                .getLimit(3)
                .useLimit(3)
                .useCustomCode(false)
                .bindOpenid(false)
                .canShare(true)
                .canGiveFriend(true)
                .customUrlName("立即使用")
                .customUrl("http://www.qq.com")
                .customUrlSubTitle("6个汉字tips")
                .promotionUrlName("更多优惠")
                .promotionUrl("http://www.qq.com")
                .build();

        WxMpGrouponCard wxMpGrouponCard = WxMpGrouponCard.builder()
                .cardType("GROUPON")
                .groupon(WxMpGrouponCardDetail.builder()
                        .baseInfo(baseInfo)
                        .dealDetail("以下锅底2选1（有菌王锅、麻辣锅、大骨锅、番茄锅、清补凉锅、酸 菜鱼锅可选）： 大锅1份 12元 小锅2份 16元")
                        .build())
                .build();

        WxMpCardCreateRequest wxMpCardCreateRequest = WxMpCardCreateRequest.builder()
                .card(wxMpGrouponCard)
                .build();
        try {
            createCard(wxMpCardCreateRequest);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    public static void createCard(WxMpCardCreateRequest wxMpGrouponCard) throws WxErrorException {
        System.out.println(wxMpGrouponCard.toJson());
    }
}
