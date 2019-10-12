package com.naspat.mp.bean.message;

import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.mp.builder.outxml.*;
import com.naspat.mp.config.WxMpConfig;
import com.naspat.mp.util.crypto.WxMpCryptUtils;
import com.naspat.mp.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

@Data
@XStreamAlias("xml")
public abstract class WxMpXmlOutMessage implements Serializable {
    private static final long serialVersionUID = 3748241701525131054L;

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String fromUserName;

    @XStreamAlias("CreateTime")
    protected Long createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String msgType;

    /**
     * 获得文本消息builder
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得音乐消息builder
     */
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    /**
     * 获得图文消息builder
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    /**
     * 获得客服消息builder
     */
    public static TransferCustomerServiceBuilder TRANSFER_CUSTOMER_SERVICE() {
        return new TransferCustomerServiceBuilder();
    }

    @SuppressWarnings("unchecked")
    public String toXml() {
        return XStreamTransformer.toXml((Class<WxMpXmlOutMessage>) this.getClass(), this);
    }

    /**
     * 转换成加密的xml格式
     */
    public String toEncryptedXml(WxMpConfig wxMpConfig) {
        String plainXml = toXml();
        WxMpCryptUtils pc = new WxMpCryptUtils(wxMpConfig);
        return pc.encrypt(plainXml);
    }
}
