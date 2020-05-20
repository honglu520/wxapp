package com.eddy.health.aide.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author PuaChen
 * @Create 2018-09-18 17:37
 */
public class ObjectUtil {

    /**
     * 对象序列化为Map
     *
     * @param obj
     * @return
     */
    public static CustomMap convertObjectToMap(Object obj) {
        String json = JSONObject.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty);
        return JSONObject.parseObject(json, CustomMap.class);
    }


    /**
     * 转换为  BigDecimal
     *
     * @param str
     * @return
     */
    public static BigDecimal convertBigDecimal(Object str) {
        return new BigDecimal(String.valueOf(str));
    }


    /**
     * 将Map字段Key 下划线转化为驼峰命名
     *
     * @param params
     * @return
     */
    public static CustomMap fieldCaseToCamel(Map<String, Object> params) {
        CustomMap customMap = CustomMap.create();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            if (key.contains("_")) {
                key = StringUtils.underlineToCamel(key);
            }
            customMap.put(key, entry.getValue());
        }
        return customMap;
    }

    /**
     * 判断字段不为空 如果其中一个为空返回 false
     *
     * @param objs
     * @return
     */
    public static boolean checkParamsNotNull(Object... objs) {
        for (Object o : objs) {
            if (o instanceof Integer) {
                if (o == null) {
                    return false;
                }
            } else if (o instanceof String) {
                if (org.apache.commons.lang3.StringUtils.isBlank((String) o)) {
                    return false;
                }
            } else if (o instanceof Map) {
                if (!FieldCheckUtil.isMapFieldNotNull((Map) o)) {
                    return false;
                }
            } else {
                if (o == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取文件大小
     *
     * @param size
     * @return
     */
    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }
}
