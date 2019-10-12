package com.naspat.mp.api;

import com.naspat.common.error.WxErrorException;
import com.naspat.mp.bean.data_cube.*;

import java.util.Date;
import java.util.List;

/**
 * 统计分析相关接口
 */
public interface WxMpDataCubeService {
    String GET_USER_SUMMARY = "https://api.weixin.qq.com/data_cube/getusersummary";
    String GET_USER_CUMULATE = "https://api.weixin.qq.com/data_cube/getusercumulate";
    String GET_ARTICLE_SUMMARY = "https://api.weixin.qq.com/data_cube/getarticlesummary";
    String GET_ARTICLE_TOTAL = "https://api.weixin.qq.com/data_cube/getarticletotal";
    String GET_USER_READ = "https://api.weixin.qq.com/data_cube/getuserread";
    String GET_USER_READ_HOUR = "https://api.weixin.qq.com/data_cube/getuserreadhour";
    String GET_USER_SHARE = "https://api.weixin.qq.com/data_cube/getusershare";
    String GET_USER_SHARE_HOUR = "https://api.weixin.qq.com/data_cube/getusersharehour";
    String GET_UPSTREAM_MSG = "https://api.weixin.qq.com/data_cube/getupstreammsg";
    String GET_UPSTREAM_MSG_HOUR = "https://api.weixin.qq.com/data_cube/getupstreammsghour";
    String GET_UPSTREAM_MSG_WEEK = "https://api.weixin.qq.com/data_cube/getupstreammsgweek";
    String GET_UPSTREAM_MSG_MONTH = "https://api.weixin.qq.com/data_cube/getupstreammsgmonth";
    String GET_UPSTREAM_MSG_DIST = "https://api.weixin.qq.com/data_cube/getupstreammsgdist";
    String GET_UPSTREAM_MSG_DIST_WEEK = "https://api.weixin.qq.com/data_cube/getupstreammsgdistweek";
    String GET_UPSTREAM_MSG_DIST_MONTH = "https://api.weixin.qq.com/data_cube/getupstreammsgdistmonth";
    String GET_INTERFACE_SUMMARY = "https://api.weixin.qq.com/data_cube/getinterfacesummary";
    String GET_INTERFACE_SUMMARY_HOUR = "https://api.weixin.qq.com/data_cube/getinterfacesummaryhour";

    //*******************用户分析数据接口***********************//

    /**
     * <pre>
     * 获取用户增减数据
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度7天，endDate不能早于begingDate
     */
    List<WxMpDataCubeUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取累计用户数据
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN">用户分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度7天，endDate不能早于begingDate
     */
    List<WxMpDataCubeUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException;

    //*******************图文分析数据接口***********************//

    /**
     * <pre>
     * 获取图文群发每日数据（getarticlesummary）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度1天，endDate不能早于begingDate
     */
    List<WxMpDataCubeArticleResult> getArticleSummary(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取图文群发总数据（getarticletotal）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度1天，endDate不能早于begingDate
     */
    List<WxMpDataCubeArticleTotal> getArticleTotal(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取图文统计数据（getuserread）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度3天，endDate不能早于begingDate
     */
    List<WxMpDataCubeArticleResult> getUserRead(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取图文统计分时数据（getuserreadhour）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度1天，endDate不能早于begingDate
     */
    List<WxMpDataCubeArticleResult> getUserReadHour(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取图文分享转发数据（getusershare）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度7天，endDate不能早于begingDate
     */
    List<WxMpDataCubeArticleResult> getUserShare(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取图文分享转发分时数据（getusersharehour）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN">图文分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度1天，endDate不能早于begingDate
     */
    List<WxMpDataCubeArticleResult> getUserShareHour(Date beginDate, Date endDate) throws WxErrorException;

    //*******************消息分析数据接口***********************//

    /**
     * <pre>
     * 获取消息发送概况数据（getupstreammsg）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsg?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度7天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsg(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取消息分送分时数据（getupstreammsghour）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度1天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsgHour(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取消息发送周数据（getupstreammsgweek）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsgweek?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度30天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsgWeek(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取消息发送月数据（getupstreammsgmonth）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsgmonth?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度30天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsgMonth(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取消息发送分布数据（getupstreammsgdist）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度15天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsgDist(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取消息发送分布周数据（getupstreammsgdistweek）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsgdistweek?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度30天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsgDistWeek(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取消息发送分布月数据（getupstreammsgdistmonth）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN">消息分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度30天，endDate不能早于begingDate
     */
    List<WxMpDataCubeMsgResult> getUpstreamMsgDistMonth(Date beginDate, Date endDate) throws WxErrorException;

    //*******************接口分析数据接口***********************//

    /**
     * <pre>
     * 获取接口分析数据（getinterfacesummary）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141086&token=&lang=zh_CN">接口分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getinterfacesummary?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度30天，endDate不能早于begingDate
     */
    List<WxMpDataCubeInterfaceResult> getInterfaceSummary(Date beginDate, Date endDate) throws WxErrorException;

    /**
     * <pre>
     * 获取接口分析分时数据（getinterfacesummaryhour）
     * 详情请见文档：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141086&token=&lang=zh_CN">接口分析数据接口</a>
     * 接口url格式：https://api.weixin.qq.com/datacube/getinterfacesummaryhour?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param beginDate 开始时间
     * @param endDate   最大时间跨度1天，endDate不能早于begingDate
     */
    List<WxMpDataCubeInterfaceResult> getInterfaceSummaryHour(Date beginDate, Date endDate) throws WxErrorException;

}
