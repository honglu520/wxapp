package com.eddy.health.aide.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.HashMap;


public class JsonResult<T> extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SUCCESSCODE = "0";
    public static final String SUCCESSMSG = "ok";
    public static final String FAILEDCODE = "400";
//    public static final String FAILCODE = "402";

    /**
     * 返回结果集, 无记录条数, 和成功/错误信息+errCode
     *
     * @param object  结果集
     * @param errCode
     * @param errMsg
     * @return
     */
    public JsonResult put(Object object, String errCode, String errMsg) {
        super.put("result", object);
        super.put("errorCode", errCode);
        super.put("errorMessage", errMsg);
        return this;
    }

    public static JsonResult success(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.superPut("result", data);
        jsonResult.superPut("errorCode", JsonResult.SUCCESSCODE);
        return jsonResult;
    }

    public static JsonResult success() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.superPut("errorCode", JsonResult.SUCCESSCODE);
        return jsonResult;
    }

    /**
     * 判断是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        String code = (String) this.get("errorCode");
        return code.equals(SUCCESSCODE);
    }

    public static JsonResult error(String errorMsg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.superPut("errorMessage", errorMsg);
        jsonResult.superPut("errorCode", JsonResult.FAILEDCODE);
        return jsonResult;
    }

    public static JsonResult error(String errorMsg, int code) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.superPut("errorMessage", errorMsg);
        jsonResult.superPut("errorCode", code + "");
        return jsonResult;
    }

    /**
     * 返回错误的信息
     *
     * @return
     */
    public String getErrorMessage() {
        return MapUtils.getString(this, "errorMessage");
    }

    /**
     * 返回错误的代码
     *
     * @return
     */
    public int getErrorCode() {
        return MapUtils.getIntValue(this, "errorCode");
    }

    /**
     * 返回数据
     *
     * @return
     */
    public T getResult() {
        return (T) this.get("result");
    }

    /**
     * 返回结果集, 带记录条数
     *
     * @param object 结果集
     * @param num    总记录条数
     * @return
     */
    public JsonResult put(Object object, String num) {
        super.put("result", object);
        super.put("errorCode", SUCCESSCODE);
        super.put("total", num);
        return this;
    }

    /**
     * 返回结果集, 无记录条数
     *
     * @param object 结果集
     * @return
     */
    public JsonResult put(Object object) {
        super.put("result", object);
        super.put("errorCode", SUCCESSCODE);
        return this;
    }

    /**
     * 返回成功/错误信息+errCode
     *
     * @param errMsg  结果集
     * @param errCode errorcode
     * @return
     */
    public JsonResult put(String errMsg, String errCode) {
        super.put("errorCode", errCode);
        super.put("errorMessage", errMsg);
        return this;
    }


    public JsonResult superPut(String key, Object val) {
        super.put(key, val);
        return this;
    }

    /**
     * 返回成功信息+errCode
     *
     * @return
     */
    public JsonResult putSuccess() {
        super.put("errorCode", SUCCESSCODE);
        super.put("errorMessage", SUCCESSMSG);
        return this;
    }

    /**
     * 返回成功信息+errCode
     *
     * @return
     */
    public JsonResult putSuccess(String msg) {
        super.put("errorCode", SUCCESSCODE);
        super.put("errorMessage", msg);
        return this;
    }

    /**
     * 返回失败信息+errCode
     *
     * @param errMsg 结果集
     * @return
     */
    public JsonResult putFailed(String errMsg) {
        super.put("errorCode", FAILEDCODE);
        super.put("errorMessage", errMsg);
        return this;
    }

//    /**
////     * 返回业务失败信息+errCode
////     *
////     * @param errMsg 结果集
////     * @return
////     */
////    public JsonResult putFailed(String errMsg) {
////        super.put("errorCode", FAILCODE);
////        super.put("errorMessage", errMsg);
////        return this;
////    }

    public String toJson() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 参数不完整
     *
     * @return
     */
    public static JsonResult errorForEmpty() {
        return JsonResult.error("参数不完整");
    }

    public static JsonResult actionSuccess() {
        return JsonResult.success("操作成功!");
    }

    public static JsonResult actionFailure() {
        return JsonResult.error("操作失败!");
    }
}
