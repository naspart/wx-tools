package com.rolbel.ma.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.ma.api.WxMaAnalysisService;
import com.rolbel.ma.api.WxMaService;
import com.rolbel.ma.bean.analysis.*;
import com.rolbel.ma.util.json.WxMaGsonBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class WxMaAnalysisServiceImpl implements WxMaAnalysisService {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private WxMaService wxMaService;

    public WxMaAnalysisServiceImpl(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    @Override
    public List<WxMaSummaryTrend> getDailySummaryTrend(Date beginDate, Date endDate) throws WxErrorException {
        return getAnalysisResultAsList(GET_DAILY_SUMMARY_TREND_URL, beginDate, endDate,
                new TypeToken<List<WxMaSummaryTrend>>() {
                }.getType());
    }

    @Override
    public List<WxMaVisitTrend> getDailyVisitTrend(Date beginDate, Date endDate) throws WxErrorException {
        return getAnalysisResultAsList(GET_DAILY_VISIT_TREND_URL, beginDate, endDate,
                new TypeToken<List<WxMaVisitTrend>>() {
                }.getType());
    }

    @Override
    public List<WxMaVisitTrend> getWeeklyVisitTrend(Date beginDate, Date endDate) throws WxErrorException {
        return getAnalysisResultAsList(GET_WEEKLY_VISIT_TREND_URL, beginDate, endDate,
                new TypeToken<List<WxMaVisitTrend>>() {
                }.getType());
    }

    @Override
    public List<WxMaVisitTrend> getMonthlyVisitTrend(Date beginDate, Date endDate) throws WxErrorException {
        return getAnalysisResultAsList(GET_MONTHLY_VISIT_TREND_URL, beginDate, endDate,
                new TypeToken<List<WxMaVisitTrend>>() {
                }.getType());
    }

    @Override
    public WxMaVisitDistribution getVisitDistribution(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMaService.post(GET_VISIT_DISTRIBUTION_URL, toJson(beginDate, endDate));
        return WxMaVisitDistribution.fromJson(responseContent);
    }

    @Override
    public WxMaRetainInfo getDailyRetainInfo(Date beginDate, Date endDate) throws WxErrorException {
        return getRetainInfo(beginDate, endDate, GET_DAILY_RETAIN_INFO_URL);
    }

    @Override
    public WxMaRetainInfo getWeeklyRetainInfo(Date beginDate, Date endDate) throws WxErrorException {
        return getRetainInfo(beginDate, endDate, GET_WEEKLY_RETAIN_INFO_URL);
    }

    @Override
    public WxMaRetainInfo getMonthlyRetainInfo(Date beginDate, Date endDate) throws WxErrorException {
        return getRetainInfo(beginDate, endDate, GET_MONTHLY_RETAIN_INFO_URL);
    }

    @Override
    public List<WxMaVisitPage> getVisitPage(Date beginDate, Date endDate) throws WxErrorException {
        return getAnalysisResultAsList(GET_VISIT_PAGE_URL, beginDate, endDate,
                new TypeToken<List<WxMaVisitPage>>() {
                }.getType());
    }

    @Override
    public WxMaUserPortrait getUserPortrait(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMaService.post(GET_USER_PORTRAIT_URL, toJson(beginDate, endDate));
        return WxMaUserPortrait.fromJson(responseContent);
    }

    private WxMaRetainInfo getRetainInfo(Date beginDate, Date endDate, String url) throws WxErrorException {
        String responseContent = this.wxMaService.post(url, toJson(beginDate, endDate));
        return WxMaRetainInfo.fromJson(responseContent);
    }

    /**
     * 获取数据分析结果并返回 List，returnType 类型
     *
     * @param url        链接
     * @param returnType 返回的类型
     * @param <T>        返回的类型
     * @return List 类型的数据
     */
    private <T> List<T> getAnalysisResultAsList(String url, Date beginDate, Date endDate, Type returnType) throws WxErrorException {
        String responseContent = this.wxMaService.post(url, toJson(beginDate, endDate));
        JsonObject response = JSON_PARSER.parse(responseContent).getAsJsonObject();
        boolean hasList = response.has("list");
        if (hasList) {
            return WxMaGsonBuilder.create().fromJson(response.getAsJsonArray("list"), returnType);
        } else {
            return null;
        }
    }

    private static String toJson(Date beginDate, Date endDate) {
        JsonObject param = new JsonObject();
        param.addProperty("begin_date", DateFormatUtils.format(beginDate, "yyyyMMdd"));
        param.addProperty("end_date", DateFormatUtils.format(endDate, "yyyyMMdd"));
        return param.toString();
    }
}
