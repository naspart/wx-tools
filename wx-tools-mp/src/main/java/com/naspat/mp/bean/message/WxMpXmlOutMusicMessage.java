package com.naspat.mp.bean.message;

import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@XStreamAlias("xml")
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutMusicMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = 4844634374337427257L;

    @XStreamAlias("Music")
    protected final Music music = new Music();

    public WxMpXmlOutMusicMessage() {
        this.msgType = WxConstant.XmlMsgType.MUSIC;
    }

    @Data
    @XStreamAlias("Music")
    public static class Music implements Serializable {
        private static final long serialVersionUID = -5393244675372053725L;

        @XStreamAlias("Title")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String title;

        @XStreamAlias("Description")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String description;

        @XStreamAlias("ThumbMediaId")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String thumbMediaId;

        @XStreamAlias("MusicUrl")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String musicUrl;

        @XStreamAlias("HQMusicUrl")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String hqMusicUrl;
    }

    public String getTitle() {
        return this.music.title;
    }

    public void setTitle(String title) {
        this.music.title = title;
    }

    public String getDescription() {
        return this.music.description;
    }

    public void setDescription(String description) {
        this.music.description = description;
    }

    public String getThumbMediaId() {
        return this.music.thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.music.thumbMediaId = thumbMediaId;
    }

    public String getMusicUrl() {
        return this.music.musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.music.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
        return this.music.hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.music.hqMusicUrl = hqMusicUrl;
    }

}
