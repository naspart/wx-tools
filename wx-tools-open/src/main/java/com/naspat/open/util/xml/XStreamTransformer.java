package com.naspat.open.util.xml;

import com.naspat.common.util.xml.XStreamInitializer;
import com.naspat.open.bean.message.WxOpenXmlMessage;
import com.thoughtworks.xstream.XStream;

import java.io.InputStream;
import java.util.*;

public class XStreamTransformer {
    private static final Map<Class<?>, XStream> CLASS_2_XSTREAM_INSTANCE = new HashMap<>();

    static {
        registerClass(WxOpenXmlMessage.class);
    }

    /**
     * xml字符串转换成Java bean.
     *
     * @param clazz 对象class
     * @param <T>   泛型对象
     * @param xml   xml字符串
     * @return Java bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(Class<T> clazz, String xml) {
        return (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(xml);
    }

    /**
     * xml字符串流转换成Java bean.
     *
     * @param clazz 对象class
     * @param is    xml字符串流
     * @param <T>   泛型对象
     * @return Java bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXml(Class<T> clazz, InputStream is) {
        return (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(is);
    }

    /**
     * Java bean转换成xml字符串.
     *
     * @param clazz  对象class
     * @param object Java bean
     * @param <T>    泛型对象
     * @return xml字符串
     */
    public static <T> String toXml(Class<T> clazz, T object) {
        return CLASS_2_XSTREAM_INSTANCE.get(clazz).toXML(object);
    }

    /**
     * 注册扩展消息的解析器.
     *
     * @param clz     类型
     * @param xStream xml解析器
     */
    public static void register(Class<?> clz, XStream xStream) {
        CLASS_2_XSTREAM_INSTANCE.put(clz, xStream);
    }

    /**
     * 会自动注册该类及其子类.
     *
     * @param clz 要注册的类
     */
    private static void registerClass(Class<?> clz) {
        XStream xstream = XStreamInitializer.getInstance();
        xstream.setClassLoader(Thread.currentThread().getContextClassLoader());

        xstream.processAnnotations(clz);
        xstream.allowTypes(new Class[]{clz});
        xstream.processAnnotations(getInnerClasses(clz));
        if (clz.equals(WxOpenXmlMessage.class)) {
            // 操蛋的微信，模板消息推送成功的消息是MsgID，其他消息推送过来是MsgId
            xstream.aliasField("MsgID", WxOpenXmlMessage.class, "msgId");
        }

        register(clz, xstream);
    }

    private static Class<?>[] getInnerClasses(Class<?> clz) {
        Class<?>[] innerClasses = clz.getClasses();
        if (innerClasses == null) {
            return null;
        }

        List<Class<?>> result = new ArrayList<>();
        result.addAll(Arrays.asList(innerClasses));
        for (Class<?> inner : innerClasses) {
            Class<?>[] innerClz = getInnerClasses(inner);
            if (innerClz == null) {
                continue;
            }

            result.addAll(Arrays.asList(innerClz));
        }

        return result.toArray(new Class<?>[0]);
    }
}
