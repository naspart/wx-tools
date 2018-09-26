package com.rolbel.mp.api.impl;

import com.google.gson.JsonObject;
import com.rolbel.common.error.WxErrorException;
import com.rolbel.mp.api.WxMpDataCubeService;
import com.rolbel.mp.api.WxMpService;
import com.rolbel.mp.bean.data_cube.*;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.Format;
import java.util.Date;
import java.util.List;

/**
 * 数据统计相关接口的实现类
 */
public class WxMpDataCubeServiceImpl implements WxMpDataCubeService {

    private final Format dateFormat = FastDateFormat.getInstance("yyyy-MM-dd");

    private WxMpService wxMpService;

    WxMpDataCubeServiceImpl(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public List<WxMpDataCubeUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_USER_SUMMARY, buildParams(beginDate, endDate));
        return WxMpDataCubeUserSummary.fromJson(responseContent);
    }

    @Override
    public List<WxMpDataCubeUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_USER_CUMULATE, buildParams(beginDate, endDate));

        return WxMpDataCubeUserCumulate.fromJson(responseContent);
    }

    @Override
    public List<WxMpMpDataCubeArticleResult> getArticleSummary(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_ARTICLE_SUMMARY, beginDate, endDate);
    }

    @Override
    public List<WxMpMpDataCubeArticleTotal> getArticleTotal(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_ARTICLE_TOTAL, buildParams(beginDate, endDate));
        return WxMpMpDataCubeArticleTotal.fromJson(responseContent);
    }

    @Override
    public List<WxMpMpDataCubeArticleResult> getUserRead(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_READ, beginDate, endDate);
    }

    @Override
    public List<WxMpMpDataCubeArticleResult> getUserReadHour(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_READ_HOUR, beginDate, endDate);
    }

    @Override
    public List<WxMpMpDataCubeArticleResult> getUserShare(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_SHARE, beginDate, endDate);
    }

    @Override
    public List<WxMpMpDataCubeArticleResult> getUserShareHour(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_SHARE_HOUR, beginDate, endDate);
    }

    private List<WxMpMpDataCubeArticleResult> getArticleResults(String url, Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(url, buildParams(beginDate, endDate));
        return WxMpMpDataCubeArticleResult.fromJson(responseContent);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsg(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG, beginDate, endDate);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsgHour(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_HOUR, beginDate, endDate);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsgWeek(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_WEEK, beginDate, endDate);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsgMonth(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_MONTH, beginDate, endDate);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsgDist(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_DIST, beginDate, endDate);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsgDistWeek(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_DIST_WEEK, beginDate, endDate);
    }

    @Override
    public List<WxMpDataCubeMsgResult> getUpstreamMsgDistMonth(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_DIST_MONTH, beginDate, endDate);
    }

    private List<WxMpDataCubeMsgResult> getUpstreamMsg(String url, Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(url, buildParams(beginDate, endDate));
        return WxMpDataCubeMsgResult.fromJson(responseContent);
    }

    @Override
    public List<WxMpDataCubeInterfaceResult> getInterfaceSummary(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_INTERFACE_SUMMARY, buildParams(beginDate, endDate));
        return WxMpDataCubeInterfaceResult.fromJson(responseContent);
    }

    private String buildParams(Date beginDate, Date endDate) {
        JsonObject param = new JsonObject();
        param.addProperty("begin_date", this.dateFormat.format(beginDate));
        param.addProperty("end_date", this.dateFormat.format(endDate));
        return param.toString();
    }

    @Override
    public List<WxMpDataCubeInterfaceResult> getInterfaceSummaryHour(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_INTERFACE_SUMMARY_HOUR, buildParams(beginDate, endDate));
        return WxMpDataCubeInterfaceResult.fromJson(responseContent);
    }
}
