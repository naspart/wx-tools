package com.naspat.pay.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信通知接口头部信息，需要做签名验证
 * 文档地址: https://wechatpay-api.gitbook.io/wechatpay-api-v3/qian-ming-zhi-nan-1/qian-ming-yan-zheng
 */
@Data
@NoArgsConstructor
public class SignatureHeader implements Serializable {
  private static final long serialVersionUID = -6958015499416059949L;
  /**
   * 时间戳
   */
  private String timeStamp;

  /**
   * 随机串
   */
  private String nonce;

  /**
   * 已签名字符串
   */
  private String signed;

  /**
   * 证书序列号
   */
  private String serialNo;
}