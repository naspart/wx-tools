package com.rolbel.mp.bean.message;

import com.rolbel.common.util.ToStringUtils;
import com.rolbel.common.util.xml.XStreamCDataConverter;
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
        return ToStringUtils.toSimpleString(this);
    }

    /**
     * 扫描类型，一般是qrcode
     */
    public String getScanType() {
        return this.scanType;
    }

    /**
     * 扫描结果，即二维码对应的字符串信息
     */
    public String getScanResult() {
        return this.scanResult;
    }
}
