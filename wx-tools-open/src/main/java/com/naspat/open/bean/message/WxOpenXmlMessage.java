package com.naspat.open.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.mp.bean.message.WxMpXmlMessage;
import com.naspat.mp.bean.message.WxMpXmlOutMessage;
import com.naspat.open.config.WxOpenConfig;
import com.naspat.open.util.WxOpenCryptUtils;
import com.naspat.open.util.xml.XStreamTransformer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@XStreamAlias("xml")
@Data
public class WxOpenXmlMessage implements Serializable {
    private static final long serialVersionUID = 6427564824451910229L;

    @XStreamAlias("AppId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String appId;

    @XStreamAlias("CreateTime")
    private Long createTime;

    @XStreamAlias("InfoType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String infoType;

    @XStreamAlias("ComponentVerifyTicket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String componentVerifyTicket;

    @XStreamAlias("AuthorizerAppid")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String authorizerAppid;

    @XStreamAlias("AuthorizationCode")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String authorizationCode;

    @XStreamAlias("AuthorizationCodeExpiredTime")
    private Long authorizationCodeExpiredTime;

    @XStreamAlias("PreAuthCode")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String preAuthCode;

    public static String wxMpOutXmlMessageToEncryptedXml(WxMpXmlOutMessage message, WxOpenConfig wxOpenConfig) {
        String plainXml = message.toXml();
        WxOpenCryptUtils pc = new WxOpenCryptUtils(wxOpenConfig);
        return pc.encrypt(plainXml);
    }

    public static WxOpenXmlMessage fromXml(String xml) {
        //修改微信变态的消息内容格式，方便解析
        xml = xml.replace("</PicList><PicList>", "");
        return XStreamTransformer.fromXml(WxOpenXmlMessage.class, xml);
    }

    public static WxOpenXmlMessage fromXml(InputStream is) {
        return XStreamTransformer.fromXml(WxOpenXmlMessage.class, is);
    }

    /**
     * 从加密字符串转换
     *
     * @param encryptedXml        密文
     * @param wxOpenConfig 配置存储器对象
     * @param timestamp           时间戳
     * @param nonce               随机串
     * @param msgSignature        签名串
     */
    public static WxOpenXmlMessage fromEncryptedXml(String encryptedXml,
                                                    WxOpenConfig wxOpenConfig, String timestamp, String nonce,
                                                    String msgSignature) {
        WxOpenCryptUtils cryptUtil = new WxOpenCryptUtils(wxOpenConfig);
        String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce,
                encryptedXml);
        return fromXml(plainText);
    }

    public static WxMpXmlMessage fromEncryptedMpXml(String encryptedXml,
                                                    WxOpenConfig wxOpenConfig, String timestamp, String nonce,
                                                    String msgSignature) {
        WxOpenCryptUtils cryptUtil = new WxOpenCryptUtils(wxOpenConfig);
        String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce,
                encryptedXml);
        return WxMpXmlMessage.fromXml(plainText);
    }

    public static WxOpenXmlMessage fromEncryptedXml(InputStream is,
                                                    WxOpenConfig wxOpenConfig, String timestamp, String nonce,
                                                    String msgSignature) {
        try {
            return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxOpenConfig,
                    timestamp, nonce, msgSignature);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
