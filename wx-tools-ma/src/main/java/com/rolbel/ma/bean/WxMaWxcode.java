package com.rolbel.ma.bean;

import com.rolbel.ma.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Element
 * @date 2017/7/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaWxcode extends AbstractWxMaQrcodeWrapper implements Serializable {
  private static final long serialVersionUID = 1287399621649210322L;

  private String path;
  private int width = 430;

  @SerializedName("auto_color")
  private boolean autoColor = true;

  @SerializedName("line_color")
  private WxMaCodeLineColor lineColor = new WxMaCodeLineColor("0", "0", "0");

  public static WxMaWxcode fromJson(String json) {
    return WxMaGsonBuilder.create().fromJson(json, WxMaWxcode.class);
  }

}
