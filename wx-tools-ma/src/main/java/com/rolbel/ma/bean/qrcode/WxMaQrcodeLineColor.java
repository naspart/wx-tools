package com.rolbel.ma.bean.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <pre>
 * lineColor 包装类
 * 用于描述二维码（小程序码）颜色（RGB参数值），
 * 详情请查看文档 https://mp.weixin.qq.com/debug/wxadoc/dev/api/qrcode.html
 * </pre>
 */
@Data
@AllArgsConstructor
public class WxMaQrcodeLineColor {
  private String r = "0", g = "0", b = "0";
}
