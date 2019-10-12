package com.naspat.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.cp.bean.outxml_builder.*;
import com.naspat.cp.config.WxCpConfigStorage;
import com.naspat.cp.util.crypto.WxCpCryptUtil;
import com.naspat.cp.util.xml.XStreamTransformer;

import java.io.Serializable;

@XStreamAlias("xml")
@Data
public abstract class WxCpXmlOutMessage implements Serializable {
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
     * 获得文本消息builder.
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder.
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder.
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder.
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得图文消息builder.
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    @SuppressWarnings("all")
    protected String toXml() {
        return XStreamTransformer.toXml((Class) this.getClass(), this);
    }

    /**
     * 转换成加密的xml格式.
     */
    public String toEncryptedXml(WxCpConfigStorage wxCpConfigStorage) {
        String plainXml = toXml();
        WxCpCryptUtil pc = new WxCpCryptUtil(wxCpConfigStorage);
        return pc.encrypt(plainXml);
    }
}
