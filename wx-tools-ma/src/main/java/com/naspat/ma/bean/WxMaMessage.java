package com.naspat.ma.bean;

import com.google.gson.annotations.SerializedName;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.ma.config.WxMaConfig;
import com.naspat.ma.util.crypt.WxMaCryptUtils;
import com.naspat.ma.util.json.WxMaGsonBuilder;
import com.naspat.ma.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@XStreamAlias("xml")
@Data
public class WxMaMessage implements Serializable {
    private static final long serialVersionUID = 4587325553155337449L;

    @SerializedName("Encrypt")
    @XStreamAlias("Encrypt")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String encrypt;

    @SerializedName("ToUserName")
    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String toUser;

    @SerializedName("FromUserName")
    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String fromUser;

    @SerializedName("CreateTime")
    @XStreamAlias("CreateTime")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private Integer createTime;

    @SerializedName("MsgType")
    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType;

    @SerializedName("MsgDataFormat")
    @XStreamAlias("MsgDataFormat")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgDataFormat;

    @SerializedName("Content")
    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    @SerializedName("MsgId")
    @XStreamAlias("MsgId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private Long msgId;

    @SerializedName("PicUrl")
    @XStreamAlias("PicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String picUrl;

    @SerializedName("MediaId")
    @XStreamAlias("MediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String mediaId;

    @SerializedName("Event")
    @XStreamAlias("Event")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String event;

    @SerializedName("SessionFrom")
    @XStreamAlias("SessionFrom")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String sessionFrom;

    public static WxMaMessage fromXml(String xml) {
        return XStreamTransformer.fromXml(WxMaMessage.class, xml);
    }

    public static WxMaMessage fromXml(InputStream is) {
        return XStreamTransformer.fromXml(WxMaMessage.class, is);
    }

    /**
     * 从加密字符串转换.
     *
     * @param encryptedXml 密文
     * @param wxMaConfig   配置存储器对象
     * @param timestamp    时间戳
     * @param nonce        随机串
     * @param msgSignature 签名串
     */
    public static WxMaMessage fromEncryptedXml(String encryptedXml,
                                               WxMaConfig wxMaConfig, String timestamp, String nonce,
                                               String msgSignature) {
        String plainText = new WxMaCryptUtils(wxMaConfig).decrypt(msgSignature, timestamp, nonce, encryptedXml);
        return fromXml(plainText);
    }

    public static WxMaMessage fromEncryptedXml(InputStream is, WxMaConfig wxMaConfig, String timestamp,
                                               String nonce, String msgSignature) {
        try {
            return fromEncryptedXml(IOUtils.toString(is, StandardCharsets.UTF_8), wxMaConfig,
                    timestamp, nonce, msgSignature);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static WxMaMessage fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaMessage.class);
    }

    public static WxMaMessage fromEncryptedJson(String encryptedJson, WxMaConfig config) {
        try {
            WxMaMessage encryptedMessage = fromJson(encryptedJson);
            String plainText = new WxMaCryptUtils(config).decrypt(encryptedMessage.getEncrypt());
            return fromJson(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static WxMaMessage fromEncryptedJson(InputStream inputStream, WxMaConfig config) {
        try {
            return fromEncryptedJson(IOUtils.toString(inputStream, StandardCharsets.UTF_8), config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }

}
