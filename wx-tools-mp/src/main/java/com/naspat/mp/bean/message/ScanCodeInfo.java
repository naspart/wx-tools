package com.naspat.mp.bean.message;

import com.naspat.common.util.xml.XStreamCDataConverter;
import com.naspat.mp.util.json.WxMpGsonBuilder;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("ScanCodeInfo")
@Data
public class ScanCodeInfo implements Serializable {
    private static final long serialVersionUID = 6439894539942046381L;

    @XStreamAlias("ScanType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanType;

    @XStreamAlias("ScanResult")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanResult;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
