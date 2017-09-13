package com.common.utils.controller.validate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.common.utils.enity.User;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-15
 * <p>Version: 1.0
 */
public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, User> {

    @Override
    public void initialize(CheckPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if(user == null) {
            return true;
        }

        //没有填密码
        if(!StringUtils.hasText(user.getPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("密码为空")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        if(!StringUtils.hasText(user.getConfirPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("确认密码为空")
                    .addPropertyNode("confirPassword")
                    .addConstraintViolation();
            return false;
        }

        //两次密码不一样
        if (!user.getPassword().trim().equals(user.getConfirPassword().trim())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("密码不一致")
//            context.buildConstraintViolationWithTemplate("{password.confirmation.error}")
                    .addPropertyNode("confirPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}