package com.imooc.permission.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lesleey
 * @date 2021/5/3-20:59
 * @function 验证工具类
 */
public class BeanValidateUtil {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, Object> validate(T t, Class ... groups){
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
        if(CollectionUtils.isEmpty(validate)) return Collections.emptyMap();
        return validate.stream().collect(Collectors.toMap(tConstraintViolation ->
                        tConstraintViolation.getPropertyPath().toString(),
                ConstraintViolation::getMessage));
    }

    public static <T> Map<String, Object> validateList(Collection<?> args){
        if(CollectionUtils.isEmpty(args))
            return Collections.emptyMap();

        Iterator<?> iterator = args.iterator();
        Class[] EMPTY_ARR = new Class[0];
        Map<String, Object> errors = null;
        do{
            if(!iterator.hasNext()) return Collections.emptyMap();
            errors = validate(iterator.next(), EMPTY_ARR);
        } while(errors == null || errors.isEmpty());
        return errors;

    }

    public static <T> Map<String, Object> validateObject(T fistObject, T ... args){
        if(args == null || args.length == 0)
            return validateObject(fistObject, new Class[0]);
        return validateList(Arrays.asList(fistObject, args));
    }

    public static <T> void check(T param){
        Map<String, Object> errors = validateObject(param);
        if(MapUtils.isNotEmpty(errors)){
            throw new RuntimeException(errors.toString());
        }
    }

}
