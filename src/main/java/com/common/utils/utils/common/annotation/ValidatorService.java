package com.common.utils.utils.common.annotation;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.common.utils.utils.common.ResponseCode;
import com.common.utils.utils.common.exception.BusinessException;
import com.common.utils.utils.common.exception.MetaRestResponse;

/**
 * 注解解析.
 *
 */
@Service
public class ValidatorService {

    private Validator validator;

    /**
     * 解析的入口.
     */
    public void valid(Object object) throws Exception {
        // 获取object的类型
        Class<? extends Object> clazz = object.getClass();
        // 获取该类型声明的成员
        Field[] fields = clazz.getDeclaredFields();
        // 遍历属性
        for (Field field : fields) {
            // 对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            validate(field, object);
            // 重新设置会私有权限
            field.setAccessible(false);
        }
    }

    /**
     * 解析的入口.
     */
    public void validate(Field field, Object object) throws Exception {
        String description;
        Object value;
        // 获取对象的成员的注解信息
        validator = field.getAnnotation(Validator.class);
        value = field.get(object);

        if (validator == null) {
            return;
        }

        description = validator.description().equals("")
                ? field.getName()
                : validator.description();

        if (!validator.nullable()) {
            if (value == null || StringUtils.isBlank(value.toString())) {
                throw new BusinessException(ResponseCode.BAD_REQUEST,
                        description + "不能为空");
            }
        }

        if (value.toString().length() > validator.maxLength()
                && validator.maxLength() != 0) {
            throw new BusinessException(ResponseCode.BAD_REQUEST, description
                    + "长度不能超过" + validator.maxLength());
        }

        if (value.toString().length() < validator.minLength()
                && validator.minLength() != 0) {
            throw new BusinessException(ResponseCode.BAD_REQUEST, description
                    + "长度不能小于" + validator.minLength());
        }

        if (validator.regexType() != RegexType.NONE) {
            switch (validator.regexType()) {
                case NONE :
                    break;
                case SPECIALCHAR :
                    if (RegexUtil.hasSpecialChar(value.toString())) {
                        throw new BusinessException(ResponseCode.BAD_REQUEST,
                                description + "不能含有特殊字符");
                    }
                    break;
                case CHINESE :
                    if (RegexUtil.isChinese2(value.toString())) {
                        throw new BusinessException(ResponseCode.BAD_REQUEST,
                                description + "不能含有中文字符");
                    }
                    break;
                case EMAIL :
                    if (!RegexUtil.isEmail(value.toString())) {
                        throw new BusinessException(ResponseCode.BAD_REQUEST,
                                description + "地址格式不正确");
                    }
                    break;
                case IP :
                    if (!RegexUtil.isIp(value.toString())) {
                        throw new BusinessException(ResponseCode.BAD_REQUEST,
                                description + "地址格式不正确");
                    }
                    break;
                case NUMBER :
                    if (!RegexUtil.isNumber(value.toString())) {
                        throw new BusinessException(ResponseCode.BAD_REQUEST,
                                description + "不是数字");
                    }
                    break;
                case MOBILE :
                    if (!RegexUtil.isMobile(value.toString())) {
                        throw new BusinessException(ResponseCode.BAD_REQUEST,
                                description + "不是手机号码");
                    }
                    break;
                default :
                    break;
            }
        }

        if (!validator.regexExpression().equals("")) {
            if (value.toString().matches(validator.regexExpression())) {
                MetaRestResponse.error(ResponseCode.BAD_REQUEST, description
                        + "格式不正确");
            }
        }
    }
}
