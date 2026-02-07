package com.lz.framework.common.validation;

import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.util.validation.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {

    @Override
    public void initialize(IdCard annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果身份证为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        // 校验身份证
        return ValidationUtils.isIdCard(value);
    }

}
