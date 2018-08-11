package com.rolbel.pay.converter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rolbel.pay.bean.pappay.result.PapPayOrderNotifyResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PapPayOrderNotifyResultConverter extends AbstractReflectionConverter {
    public PapPayOrderNotifyResultConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(PapPayOrderNotifyResult.class);
    }

    @Override
    public void marshal(Object original, HierarchicalStreamWriter writer, MarshallingContext context) {
        super.marshal(original, writer, context);
        PapPayOrderNotifyResult obj = (PapPayOrderNotifyResult) original;
        List<PapPayOrderNotifyResult.PapPayOrderNotifyCoupon> list = obj.getCouponList();
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PapPayOrderNotifyResult.PapPayOrderNotifyCoupon coupon = list.get(i);
            writer.startNode("coupon_id_" + i);
            writer.setValue(coupon.getCouponId());
            writer.endNode();
            writer.startNode("coupon_fee_" + i);
            writer.setValue(coupon.getCouponFee() + "");
            writer.endNode();
        }
    }

    @Override
    protected void marshallField(MarshallingContext context, Object newObj, Field field) {
        if ("couponList".equals(field.getName())) {
            return;
        }

        super.marshallField(context, newObj, field);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        PapPayOrderNotifyResult obj = new PapPayOrderNotifyResult();

        List<Field> fields = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
        Map<String, Field> fieldMap = getFieldMap(fields);

        Map<Integer, PapPayOrderNotifyResult.PapPayOrderNotifyCoupon> coupons = Maps.newTreeMap();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if (fieldMap.containsKey(reader.getNodeName())) {
                Field field = fieldMap.get(reader.getNodeName());
                this.setFieldValue(context, obj, field);
            } else if (StringUtils.startsWith(reader.getNodeName(), "coupon_id_")) {
                String id = (String) context.convertAnother(obj, String.class);
                this.getElement(coupons, reader.getNodeName()).setCouponId(id);
            } else if (StringUtils.startsWith(reader.getNodeName(), "coupon_fee_")) {
                Integer fee = (Integer) context.convertAnother(obj, Integer.class);
                this.getElement(coupons, reader.getNodeName()).setCouponFee(fee);
            }
            reader.moveUp();
        }

        obj.setCouponList(Lists.newArrayList(coupons.values()));

        return obj;
    }

    private void setFieldValue(UnmarshallingContext context, PapPayOrderNotifyResult obj, Field field) {
        Object val = context.convertAnother(obj, field.getType());
        try {
            if (val != null) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
                pd.getWriteMethod().invoke(obj, val);
            }
        } catch (Exception ignored) {
        }
    }

    private Map<String, Field> getFieldMap(List<Field> fields) {
        return Maps.uniqueIndex(fields, field -> {
            assert field != null;
            if (field.isAnnotationPresent(XStreamAlias.class)) {
                return field.getAnnotation(XStreamAlias.class).value();
            }

            return field.getName();
        });
    }

    private PapPayOrderNotifyResult.PapPayOrderNotifyCoupon getElement(Map<Integer, PapPayOrderNotifyResult.PapPayOrderNotifyCoupon> coupons, String nodeName) {
        Integer index = Integer.valueOf(StringUtils.substringAfterLast(nodeName, "_"));
        if (coupons.get(index) == null) {
            coupons.put(index, new PapPayOrderNotifyResult.PapPayOrderNotifyCoupon());
        }

        return coupons.get(index);
    }
}
