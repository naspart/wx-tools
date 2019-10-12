package com.naspat.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.naspat.common.api.WxConstant;
import com.naspat.common.util.xml.XStreamCDataConverter;

import java.io.Serializable;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutVideoMessage extends WxCpXmlOutMessage {
    private static final long serialVersionUID = -5791485992473111352L;

    @XStreamAlias("Video")
    protected final Video video = new Video();

    public WxCpXmlOutVideoMessage() {
        this.msgType = WxConstant.XmlMsgType.VIDEO;
    }

    public String getMediaId() {
        return this.video.getMediaId();
    }

    public void setMediaId(String mediaId) {
        this.video.setMediaId(mediaId);
    }

    public String getTitle() {
        return this.video.getTitle();
    }

    public void setTitle(String title) {
        this.video.setTitle(title);
    }

    public String getDescription() {
        return this.video.getDescription();
    }

    public void setDescription(String description) {
        this.video.setDescription(description);
    }

    @Data
    @XStreamAlias("Video")
    public static class Video implements Serializable {
        private static final long serialVersionUID = -3699197506579830537L;

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
}
