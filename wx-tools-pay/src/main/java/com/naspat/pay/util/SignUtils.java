package com.naspat.pay.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.naspat.pay.bean.request.BaseWxPayRequest;
import com.naspat.pay.bean.result.BaseWxPayResult;
import com.naspat.pay.constant.WxPayConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.naspat.common.util.SignUtils.createHmacSha256Sign;

/**
 * <pre>
 * 签名相关工具类.
 * </pre>
 */
public class SignUtils {
    private static final Logger log = LoggerFactory.getLogger(SignUtils.class);

    private static List<String> NO_SIGN_PARAMS = Lists.newArrayList("sign", "key", "xmlString", "xmlDoc", "couponList");

    /**
     * 请参考并使用 {@link #createSign(Object, String, String, String[])}.
     */
    @Deprecated
    public static String createSign(Object xmlBean, String signKey) {
        return createSign(xmlBean2Map(xmlBean), signKey);
    }

    /**
     * 请参考并使用 {@link #createSign(Map, String, String, String[])} .
     *
     * @param params  the params
     * @param signKey the sign key
     * @return the string
     */
    @Deprecated
    public static String createSign(Map<String, String> params, String signKey) {
        return createSign(params, WxPayConstants.SignType.MD5, signKey, new String[0]);
    }

    /**
     * 请参考并使用 {@link #createSign(Map, String, String, String[])}.
     */
    @Deprecated
    public static String createSign(Map<String, String> params, String signKey, String[] ignoredParams) {
        return createSign(params, WxPayConstants.SignType.MD5, signKey, ignoredParams);
    }

    /**
     * 微信支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3).
     *
     * @param xmlBean       Bean里的属性如果存在XML注解，则使用其作为key，否则使用变量名
     * @param signType      签名类型，如果为空，则默认为MD5
     * @param signKey       签名Key
     * @param ignoredParams 签名时需要忽略的特殊参数
     * @return 签名字符串
     */
    public static String createSign(Object xmlBean, String signType, String signKey, String[] ignoredParams) {
        return createSign(xmlBean2Map(xmlBean), signType, signKey, ignoredParams);
    }

    /**
     * 微信支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3).
     *
     * @param params        参数信息
     * @param signType      签名类型，如果为空，则默认为MD5
     * @param signKey       签名Key
     * @param ignoredParams 签名时需要忽略的特殊参数
     * @return 签名字符串
     */
    public static String createSign(Map<String, String> params, String signType, String signKey, String[] ignoredParams) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            boolean shouldSign = false;

            if (StringUtils.isNotEmpty(value) && !ArrayUtils.contains(ignoredParams, key) && !NO_SIGN_PARAMS.contains(key)) {
                shouldSign = true;
            }

            if (shouldSign) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        toSign.append("key=").append(signKey);

        if (WxPayConstants.SignType.HMAC_SHA256.equals(signType)) {
            return createHmacSha256Sign(toSign.toString(), signKey);
        } else {
            return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
        }
    }


    /**
     * 企业微信签名
     *
     * @param signType md5 目前接口要求使用的加密类型
     */
    public static String createEntSign(String actName, String mchBillNo, String mchId, String nonceStr,
                                       String reOpenid, Integer totalAmount, String wxAppId, String signKey,
                                       String signType) {
        Map<String, String> sortedMap = new HashMap<>();
        sortedMap.put("act_name", actName);
        sortedMap.put("mch_billno", mchBillNo);
        sortedMap.put("mch_id", mchId);
        sortedMap.put("nonce_str", nonceStr);
        sortedMap.put("re_openid", reOpenid);
        sortedMap.put("total_amount", totalAmount + "");
        sortedMap.put("wxappid", wxAppId);

        Map<String, String> sortParams = new TreeMap<>(sortedMap);
        Set<Map.Entry<String, String>> entries = sortParams.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        StringBuilder toSign = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            boolean shouldSign = false;
            if (StringUtils.isNotEmpty(value)) {
                shouldSign = true;
            }

            if (shouldSign) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        //企业微信这里字段名不一样
        toSign.append("secret=").append(signKey);
        if (WxPayConstants.SignType.HMAC_SHA256.equals(signType)) {
            return createHmacSha256Sign(toSign.toString(), signKey);
        } else {
            return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
        }

    }

    /**
     * 校验签名是否正确.
     *
     * @param xmlBean  Bean需要标记有XML注解
     * @param signType 签名类型，如果为空，则默认为MD5
     * @param signKey  校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    public static boolean checkSign(Object xmlBean, String signType, String signKey) {
        return checkSign(xmlBean2Map(xmlBean), signType, signKey);
    }

    /**
     * 校验签名是否正确.
     *
     * @param params   需要校验的参数Map
     * @param signType 签名类型，如果为空，则默认为MD5
     * @param signKey  校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    public static boolean checkSign(Map<String, String> params, String signType, String signKey) {
        String sign = createSign(params, signType, signKey, new String[0]);
        return sign.equals(params.get("sign"));
    }

    /**
     * 将bean按照@XStreamAlias标识的字符串内容生成以之为key的map对象
     *
     * @param bean 包含@XStreamAlias的xml bean对象
     * @return map对象
     */
    public static Map<String, String> xmlBean2Map(Object bean) {
        Map<String, String> result = Maps.newHashMap();
        List<Field> fields = new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));
        if (bean.getClass().getSuperclass().getSuperclass() == BaseWxPayRequest.class) {
            fields.addAll(Arrays.asList(BaseWxPayRequest.class.getDeclaredFields()));
        }

        if (bean.getClass().getSuperclass().getSuperclass() == BaseWxPayResult.class) {
            fields.addAll(Arrays.asList(BaseWxPayResult.class.getDeclaredFields()));
        }

        for (Field field : fields) {
            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.get(bean) == null) {
                    field.setAccessible(isAccessible);
                    continue;
                }

                if (field.isAnnotationPresent(XStreamAlias.class)) {
                    result.put(field.getAnnotation(XStreamAlias.class).value(), field.get(bean).toString());
                } else if (!Modifier.isStatic(field.getModifiers())) {
                    //忽略掉静态成员变量
                    result.put(field.getName(), field.get(bean).toString());
                }

                field.setAccessible(isAccessible);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }

        }

        return result;
    }
}
