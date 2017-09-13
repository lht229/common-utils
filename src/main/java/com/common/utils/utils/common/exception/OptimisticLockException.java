package com.common.utils.utils.common.exception;


/**
 * 乐观锁异常.
 *
 *
 */
@SuppressWarnings("serial")
public class OptimisticLockException extends ResponseCodeAwareException {

    /**
     * 构造函数.
     *
     * @param code
     *            错误码
     * @param message
     *            错误消息
     */
    public OptimisticLockException(Integer code, String message) {
        super(code, message);
    }

}
