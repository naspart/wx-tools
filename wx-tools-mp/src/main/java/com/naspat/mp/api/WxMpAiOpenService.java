package com.naspat.mp.api;

import com.naspat.common.enums.AiLangType;
import com.naspat.common.error.WxErrorException;

import java.io.File;

/**
 * 接口 {@code WxMpAiOpenService} 微信AI开放接口（语音识别，微信翻译）.
 * <pre>
 * 功能参考：<a target="_blank" href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=21516712282KzWVE">微信公众平台 - AI开放接口</a>
 * </pre>
 */
public interface WxMpAiOpenService {
    /**
     * 语音提交接口URL
     */
    String VOICE_UPLOAD_URL = "http://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext?format=%s&voice_id=%s&lang=%s";
    /**
     * 获取语音识别结果接口URL
     */
    String VOICE_QUERY_RESULT_URL = "http://api.weixin.qq.com/cgi-bin/media/voice/queryrecoresultfortext";
    /**
     * 微信翻译接口URL
     */
    String TRANSLATE_URL = "http://api.weixin.qq.com/cgi-bin/media/voice/translatecontent?lfrom=%s&lto=%s";

    /**
     * 微信提交上传语音.
     * <pre>
     * 接口地址：{@code http://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext?access_token=ACCESS_TOKEN&format=&voice_id=xxxxxx&lang=zh_CN}
     * 请求方式：{@code POST}
     * 注意：语音内容放body里或者上传文件的形式
     * <table summary="" border="1">
     * <tr style="border:solid 1px #4D7A97;">
     *     <td>参数</td><td>是否必须</td><td>说明</td>
     * </tr>
     * <tr>
     *     <td>access_token</td><td>是</td><td>接口调用凭证</td>
     * </tr>
     * <tr>
     *     <td>format</td><td>是</td><td>文件格式（只支持mp3，16k，单声道，最大1M）</td>
     * </tr>
     * <tr>
     *     <td>voice_id</td><td>是</td><td>语音唯一标识</td>
     * </tr>
     * <tr>
     *     <td>lang</td><td>否</td><td>言，zh_CN 或 en_US，默认中文</td>
     * </tr>
     * </table>
     * </pre>
     *
     * @param voiceId   语音唯一标识
     * @param lang      语言（zh_CN 或 en_US，默认中文）
     * @param voiceFile 语音文件
     */
    void uploadVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException;

    /**
     * 获取语音识别结果.
     * <pre>
     * 接口地址：{@code http://api.weixin.qq.com/cgi-bin/media/voice/queryrecoresultfortext?access_token=ACCESS_TOKEN&voice_id=xxxxxx&lang=zh_CN}
     * 请求方式：{@code POST}
     * 注意：提交上传语音之后10s内调用这个接口
     * <table summary="" border="1">
     * <tr style="border:solid 1px #4D7A97;">
     *     <td>参数</td><td>是否必须</td><td>说明</td>
     * </tr>
     * <tr>
     *     <td>access_token</td><td>是</td><td>接口调用凭证</td>
     * </tr>
     * <tr>
     *     <td>voice_id</td><td>是</td><td>语音唯一标识</td>
     * </tr>
     * <tr>
     *     <td>lang</td><td>否</td><td>言，zh_CN 或 en_US，默认中文</td>
     * </tr>
     * </table>
     * </pre>
     *
     * @param lang    语言，zh_CN 或 en_US，默认中文
     * @param voiceId 语音唯一标识
     */
    String recogniseVoice(String voiceId, AiLangType lang) throws WxErrorException;

    /**
     * 识别指定语音文件内容.
     * <pre>
     * 此方法揉合了前两两个方法：{@link #uploadVoice} 和 {@link #recogniseVoice}
     * </pre>
     *
     * @param lang      语言，zh_CN 或 en_US，默认中文
     * @param voiceFile 语音文件
     * @param voiceId   语音唯一标识
     */
    String recogniseVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException;

    /**
     * 微信翻译.
     * <pre>
     * 接口地址：{@code http://api.weixin.qq.com/cgi-bin/media/voice/translatecontent?access_token=ACCESS_TOKEN&lfrom=xxx&lto=xxx}
     * 请求方式：{@code POST}
     * 注意：源内容放body里或者上传文件的形式（utf8格式，最大600Byte)
     * <table summary="" border="1">
     * <tr style="border:solid 1px #4D7A97;">
     *     <td>参数</td><td>是否必须</td><td>说明</td>
     * </tr>
     * <tr>
     *     <td>access_token</td><td>是</td><td>接口调用凭证</td>
     * </tr>
     * <tr>
     *     <td>lfrom</td><td>是</td><td>源语言，zh_CN 或 en_US</td>
     * </tr>
     * <tr>
     *     <td>lto</td><td>是</td><td>目标语言，zh_CN 或 en_US</td>
     * </tr>
     * </table>
     * </pre>
     *
     * @param langFrom 源语言，zh_CN 或 en_US
     * @param langTo   目标语言，zh_CN 或 en_US
     * @param content  要翻译的文本内容
     */
    String translate(AiLangType langFrom, AiLangType langTo, String content) throws WxErrorException;
}
