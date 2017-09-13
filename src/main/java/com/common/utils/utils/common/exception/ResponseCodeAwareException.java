package com.common.utils.utils.common.exception;

/**
 * 返回状态码的异常对象.
 *
 *
 */
public class ResponseCodeAwareException extends RuntimeException {

    private static final long serialVersionUID = -9164920687246694473L;

    private Integer code;

    private String message;

    /**
     * 构造函数.
     *
     * @param code
     *            状态码
     * @param message
     *            错误消息
     */
    public ResponseCodeAwareException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
