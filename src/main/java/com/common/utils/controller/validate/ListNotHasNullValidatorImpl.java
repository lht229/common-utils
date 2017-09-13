package com.common.utils.controller.validate;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 自定义注解ListNotHasNull 的实现类
 * 用于判断List集合中是否含有null元素
 */

@Service
public class ListNotHasNullValidatorImpl implements ConstraintValidator<ListNotHasNull, List> {

    private int value;

    @Override
    public void initialize(ListNotHasNull constraintAnnotation) {
        //传入value 值，可以在校验中使用
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {

        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (Object object : list) {
            if (object == null ||StringUtils.isBlank(object.toString())) {
                //如果List集合中含有Null元素，校验失败
                return false;
            }
        }
        return true;
    }

}