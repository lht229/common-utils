package com.common.utils.utils.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 切面.
 *
 * @author:haitao.liu
 *
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Validator {

    /**
     * 是否可以为空.
     */
    boolean nullable() default false;

    /**
     * 最大长度.
     */
    int maxLength() default 0;

    /**
     * 最小长度.
     */
    int minLength() default 0;

    /**
     * 提供几种常用的正则验证.
     */
    RegexType regexType() default RegexType.NONE;

    /**
     * 自定义正则验证.
     */
    String regexExpression() default "";

    /**
     * 参数或者字段描述,这样能够显示友好的异常信息.
     */
    String description() default "";
}
