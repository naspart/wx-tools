package com.naspat.mp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naspat.common.WxType;
import com.naspat.common.enums.AiLangType;
import com.naspat.common.error.WxError;
import com.naspat.common.error.WxErrorException;
import com.naspat.mp.api.WxMpAiOpenService;
import com.naspat.mp.api.WxMpService;
import com.naspat.mp.util.request_executor.voice.VoiceUploadRequestExecutor;

import java.io.File;

public class WxMpAiOpenServiceImpl implements WxMpAiOpenService {

    private static final JsonParser JSON_PARSER = new JsonParser();
    private WxMpService wxMpService;

    WxMpAiOpenServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public void uploadVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException {
        if (lang == null) {
            lang = AiLangType.zh_CN;
        }

        this.wxMpService.execute(VoiceUploadRequestExecutor.create(this.wxMpService.getRequestHttp()),
                String.format(VOICE_UPLOAD_URL, "mp3", voiceId, lang.getCode()),
                voiceFile);
    }

    @Override
    public String recogniseVoice(String voiceId, AiLangType lang) throws WxErrorException {
        if (lang == null) {
            lang = AiLangType.zh_CN;
        }

        final String responseContent = this.wxMpService.get(VOICE_QUERY_RESULT_URL,
                String.format("voice_id=%s&lang=%s", voiceId, lang.getCode()));
        final JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode") == null || jsonObject.get("errcode").getAsInt() == 0) {
            return jsonObject.get("result").getAsString();
        }

        throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
    }

    @Override
    public String recogniseVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException {
        this.uploadVoice(voiceId, lang, voiceFile);

        return this.recogniseVoice(voiceId, lang);
    }

    @Override
    public String translate(AiLangType langFrom, AiLangType langTo, String content) throws WxErrorException {
        final String responseContent = this.wxMpService.post(String.format(TRANSLATE_URL, langFrom.getCode(), langTo.getCode()), content);
        final JsonObject jsonObject = new JsonParser().parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode") == null || jsonObject.get("errcode").getAsInt() == 0) {
            return jsonObject.get("to_content").getAsString();
        }

        throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
    }
}
