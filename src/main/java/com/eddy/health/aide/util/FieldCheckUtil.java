package com.eddy.health.aide.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @Author PuaChen
 * @Create 2018-09-18 16:42
 */
public class FieldCheckUtil {

    /**
     * 判断Map 集合中的 所指定的字段是否有为空的，其中有一个成立返回false
     * 全部都不为空 返回真true
     *
     * @param map
     * @param args
     * @return
     */
    public static boolean isMapFieldNotNull(Map map, String... args) {
        if (MapUtils.isEmpty(map) || StringUtils.isAllEmpty(args)) {
            return false;
        }
        for (String arg : args) {
            if (StringUtils.isBlank(MapUtils.getString(map, arg))) {
                return false;
            }
        }
        return true;
    }


}
