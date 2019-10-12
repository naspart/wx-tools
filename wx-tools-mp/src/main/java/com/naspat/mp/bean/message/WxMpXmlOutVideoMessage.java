package com.naspat.mp.bean.message;

import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutVideoMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -6766994411172376561L;

    @XStreamAlias("Video")
    protected final Video video = new Video();

    public WxMpXmlOutVideoMessage() {
        this.msgType = WxConstant.XmlMsgType.VIDEO;
    }

    @XStreamAlias("Video")
    @Data
    public static class Video implements Serializable {
        private static final long serialVersionUID = 1537264178740972563L;

        @XStreamAlias("MediaId")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String mediaId;

        @XStreamAlias("Title")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String title;

        @XStreamAlias("Description")
        @XStreamConverter(value = XStreamCDataConverter.class)
        private String description;

    }

    public String getMediaId() {
        return this.video.mediaId;
    }

    public void setMediaId(String mediaId) {
        this.video.mediaId = mediaId;
    }

    public String getTitle() {
        return this.video.title;
    }

    public void setTitle(String title) {
        this.video.title = title;
    }

    public String getDescription() {
        return this.video.description;
    }

    public void setDescription(String description) {
        this.video.description = description;
    }
}
