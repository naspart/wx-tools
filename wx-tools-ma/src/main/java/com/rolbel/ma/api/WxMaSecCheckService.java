package com.rolbel.ma.api;

import com.rolbel.common.error.WxErrorException;

import java.io.File;

/**
 * <pre>
 * 内容安全相关接口.
 * </pre>
 */
public interface WxMaSecCheckService {

    String IMG_SEC_CHECK_URL = "https://api.weixin.qq.com/wxa/img_sec_check";

    String MSG_SEC_CHECK_URL = "https://api.weixin.qq.com/wxa/msg_sec_check";

    /**
     * <pre>
     * 校验一张图片是否含有违法违规内容.
     * 应用场景举例：
     * 1）图片智能鉴黄：涉及拍照的工具类应用(如美拍，识图类应用)用户拍照上传检测；电商类商品上架图片检测；媒体类用户文章里的图片检测等；
     * 2）敏感人脸识别：用户头像；媒体类用户文章里的图片检测；社交类用户上传的图片检测等。频率限制：单个 appId 调用上限为 1000 次/分钟，100,000 次/天
     * 详情请见: https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/imgSecCheck.html
     * </pre>
     */
    boolean checkImage(File file) throws WxErrorException;

    /**
     * <pre>
     * 检查一段文本是否含有违法违规内容。
     * 应用场景举例：
     * 用户个人资料违规文字检测；
     * 媒体新闻类用户发表文章，评论内容检测；
     * 游戏类用户编辑上传的素材(如答题类小游戏用户上传的问题及答案)检测等。 频率限制：单个 appId 调用上限为 4000 次/分钟，2,000,000 次/天*
     * 详情请见: https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/msgSecCheck.html
     * </pre>
     */
    boolean checkMessage(String msgString);
}
