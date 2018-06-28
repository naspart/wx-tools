package com.rolbel.mp.bean.message;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("HardWare")
@Data
public class HardWare implements Serializable {
    private static final long serialVersionUID = -7123957483821694505L;

    /**
     * 消息展示，目前支持myrank(排行榜)
     */
    @XStreamAlias("MessageView")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String messageView;
    /**
     * 消息点击动作，目前支持ranklist(点击跳转排行榜)
     */
    @XStreamAlias("MessageAction")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String messageAction;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
