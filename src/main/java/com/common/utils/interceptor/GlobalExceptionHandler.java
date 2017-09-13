package com.common.utils.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.utils.enity.ArgumentInvalidResult;
import com.common.utils.memcached.JsonUtil;
import com.common.utils.utils.common.ResponseCode;
import com.common.utils.utils.common.exception.MetaRestResponse;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 添加全局异常处理流程，根据需要设置需要处理的异常.
     *
     * @param request
     * @param ex
     * @param response
     * @return
     * @throws Exception
     * @throws
     */
    @ExceptionHandler(value = Exception.class)
    public MetaRestResponse MethodArgumentNotValidHandler(HttpServletRequest request, Exception ex,
            HttpServletResponse response) throws Exception {
        // MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
        List<FieldError> errorList = new ArrayList<>();
        if (ex instanceof  BindException) {
            BindException exception = (BindException) ex;
            errorList =  exception.getBindingResult().getFieldErrors();
        }else if(ex instanceof MethodArgumentNotValidException ) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            errorList =  exception.getBindingResult().getFieldErrors();

        }
        logger.info("errorList = {} ", JsonUtil.toJson(errorList));
        // 按需重新封装需要返回的错误信息
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        StringBuffer errorMsg = new StringBuffer();
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : errorList) {
            errorMsg.append(error.getDefaultMessage()).append(";");
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }
        logger.info("errorMsg = {} ", JsonUtil.toJson(invalidArguments));
        return MetaRestResponse.error(ResponseCode.BAD_REQUEST, errorMsg.toString());
    }
}