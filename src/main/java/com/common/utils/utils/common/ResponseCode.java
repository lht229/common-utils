package com.common.utils.utils.common;

/**
 * 前后端交互的状态码.
 *
 *
 */
public final class ResponseCode {

    private ResponseCode() {
    }

    /**
     * 系统未知异常提示信息.
     */
    public static final String SYSTEM_ERROR_MSG = "系统繁忙，请稍后再试.";

    /**
     * 错误状态码,在返回对象的meta.
     */
    public static final Integer ERROR = 500;

    /**
     * 错误状态码,在返回对象的meta.
     */
    public static final Integer UNKNOW_ERROR = 500;

    /**
     * 成功状态码,在返回对象的meta.
     */
    public static final Integer SUCCESS = 200;

    /**
     * 答题：答题完毕.
     */
    public static final Integer ANSWER_FINISH = 200200;

    /**
     * 答题：手机验证题，错误code.
     */
    public static final Integer ANSWER_PHONE_CHECK_ERROR = 200201;

    /**
     * 答题：答题中逻辑抛出错误提示信息.
     */
    public static final Integer ANSWER_MIDDLE_LOGIC = 200202;

    /**
     * 答题：样本数据丢失.
     */
    public static final Integer ANSWER_SIMPLE_DATA_LOSE = 200203;

    /**
     * 答题：ip限制.
     */
    public static final Integer ANSWER_IP_LIMIT = 200204;

    /**
     * 答题：设备限制.
     */
    public static final Integer ANSWER_DEVICE_LIMIT = 200205;

    /**
     * 答题：样本版本错误.
     */
    public static final Integer ANSWER_SIMPLE_VERSION_ERROR = 200206;

    /**
     * 答题：答题过程中切换问卷错误.
     */
    public static final Integer ANSWER_SIMPLE_SWITCH_SURVEY_ERROR = 200207;

    /**
     * 答题：答题校验拦截-问卷下线.<br>
     * 注意：此错误码包含情况，部署无门店、项目被删除、项目下线、不在项目周期内、无问卷
     */
    public static final Integer ANSWER_CHECK_SURVEY_OFFLINE = 200208;

    /**
     * 门店url进入答题：答题校验拦截-当前门店已不存在.<br>
     */
    public static final Integer SHOP_ANSWER_CHECK_SHOP_NOT_FIND = 200209;

    /**
     * 门店url进入答题：答题校验拦截-当前门店暂无进行中项目.<br>
     * 注意：此错误码包含情况，无默认项目、指定门店和默认项目无部署关系、指定门店和默认项目的部署无问卷、指定门店和默认项目的部署已关闭、不在默认项目周期内
     */
    public static final Integer SHOP_ANSWER_CHECK_NO_EFFECTIVE_PROJECT = 200211;

    /**
     * 支付：支付功能未开启(便于内网开发).
     */
    public static final Integer PAYMENT_TAP_OFF = 200250;

    private static final Integer CODE_401 = 401;

    private static final Integer CODE_403 = 403;

    /**
     * 没有认证通过， 需要重新登录.
     */
    public static final Integer UNAUTHORIZED = CODE_401;

    /**
     * 没有权限， 需要重新登录.
     */
    public static final Integer FORBIDDEN = CODE_403;

    /**
     * 请求参数错误统一状态码.
     */
    public static final Integer BAD_REQUEST = 400;

    /**
     * 问题分类下有问题，删除失败.
     */
    public static final Integer CATEGORY_ERROR = 400508;

    /**
     * 用户类型错误（当用大B账号登录汽后或者用汽后账号登录大B时，返回给前端的特殊错误）.
     */
    public static final Integer USER_TYPE_ERROR = 400808;

    /**
     * 邮箱找回密码时，邮箱链接失效提示错误码.
     */
    public static final Integer PASSWORD_EMAIL_ERROR = 400400;

}
