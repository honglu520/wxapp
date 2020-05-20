package com.eddy.health.aide.config;


import com.eddy.health.aide.exception.InvalidParamsException;
import com.eddy.health.aide.exception.TCException;
import com.eddy.health.aide.util.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * @Author PuaChen
 */
@RestControllerAdvice
@Log4j2
public class ErrorController {


    @ExceptionHandler(InvalidParamsException.class)
    public JsonResult invalidParamsExceptionHandler(InvalidParamsException t) {
        log.error("InvalidParamsException ", t);
        return JsonResult.error(t.getMessage(), t.getCode());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult jsonConvertError() {
        log.error("FastJson 格式转换出错 ");
        return JsonResult.error("JSON 格式有误", 500);
    }


    @ExceptionHandler(TCException.class)
    public JsonResult tcErrorHandler(TCException e) {
        log.error("程序抛出自定义异常 ", e);
        return JsonResult.error(e.getMessage(), e.getErrorCode());
    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult exception(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();

        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage());
            break;
        }
        log.error("数据验证错误 ====> " + builder.toString());
        return JsonResult.error(builder.toString());
    }

    /**
     * 处理请求单个参数不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public JsonResult constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
        log.info("处理请求单个参数不满足校验规则的异常信息 {}", exception.getMessage());
        return JsonResult.error(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public JsonResult paramsError(IllegalArgumentException e) {
        log.error("非法参数错误: ", e);
        return JsonResult.error(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public JsonResult rrException(Throwable t) {
        log.error("服务器500 ", t);
        return JsonResult.error("服务器繁忙,请稍后重试!", 500);
    }
}
