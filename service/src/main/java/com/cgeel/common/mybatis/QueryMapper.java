package com.cgeel.common.mybatis;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ZXW on 2015/1/13.
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface QueryMapper {
    Class targetEntity() default void.class;

    String mappedBy() default "";

}
