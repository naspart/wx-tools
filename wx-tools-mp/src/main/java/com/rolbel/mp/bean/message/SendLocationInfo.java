package com.rolbel.mp.bean.message;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("SendLocationInfo")
@Data
public class SendLocationInfo implements Serializable {
    private static final long serialVersionUID = -3527287141164911724L;

    @XStreamAlias("Location_X")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String locationX;

    @XStreamAlias("Location_Y")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String locationY;

    @XStreamAlias("Scale")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scale;

    @XStreamAlias("Label")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String label;

    @XStreamAlias("Poiname")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String poiName;

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }
}
