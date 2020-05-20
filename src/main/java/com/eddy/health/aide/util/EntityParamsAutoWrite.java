package com.eddy.health.aide.util;

import ch.qos.logback.core.util.ContextUtil;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author Eddy·Chen
 * @Email 835033913@qq.com
 * @Create 2019-03-02 12:22
 */
public class EntityParamsAutoWrite {

    private static void addData(Object obj, String form) {
        String method = callMethodAndLine();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                switch (field.getName()) {
                    case "createTime":
                        setTime(obj, field);
                        break;
                    case "createForm":
                        field.set(obj, form);
                        break;
                    case "createMethod":
                        field.set(obj, method);
                        break;
                    case "loseFlag":
                        field.set(obj, 1);
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setTime(Object obj, Field field) {
        try {
            field.set(obj, LocalDateTime.now());
        } catch (Exception e) {
            try {
                field.set(obj, new Date());
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void updateData(Object obj, String form) {
        String method = callMethodAndLine();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                switch (field.getName()) {
                    case "updateTime":
                        setTime(obj, field);
                        break;
                    case "updateForm":
                        field.set(obj, form);
                        break;
                    case "updateMethod":
                        field.set(obj, method);
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加数据 PC
     *
     * @param t
     * @return
     */
    public static void addForPc(Object t) {
        addData(t, "PC");
    }


    /**
     * 更新数据 PC
     *
     * @param t
     * @return
     */
    public static void updateForPc(Object t) {
        updateData(t, "PC");
    }

    /**
     * 添加数据 WeChat
     *
     * @param t
     * @return
     */
    public static void addForWeChat(Object t) {
        addData(t, "WeChat");
    }


    /**
     * 更新数据 WeChat
     *
     * @param t
     * @return
     */
    public static void updateForWeChat(Object t) {
        updateData(t, "WeChat");
    }

    /**
     * 获取执行方法的类
     *
     * @return
     */
    private static String callMethodAndLine() {
        String result = "";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
}
