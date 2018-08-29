package com.wwh.test.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年8月25日 下午1:32:49
 *
 */
public class ValidatorTest {

    public static void main(String[] args) {
        PEntity pe = new PEntity();
        pe.setName("asdf");
        pe.setSex(1);

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<PEntity>> set = validator.validate(pe, Default.class, GroupDefaultValue.class);
        if (set.isEmpty()) {
            System.out.println("为空额");
        }
        for (ConstraintViolation<PEntity> constraintViolation : set) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}
