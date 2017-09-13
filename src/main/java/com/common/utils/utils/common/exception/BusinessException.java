package com.common.utils.utils.common.exception;


/**
 * 业务层统一异常对象.
 *
 */
@SuppressWarnings("serial")
public class BusinessException extends ResponseCodeAwareException {

    /**
     * 构造函数.
     *
     * @param code
     *            错误码
     * @param message
     *            错误消息
     */
    public BusinessException(Integer code, String message) {
        super(code, message);
    }

}
