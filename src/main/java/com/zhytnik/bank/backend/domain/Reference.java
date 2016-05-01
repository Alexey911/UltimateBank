package com.zhytnik.bank.backend.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Reference {
    Class<? extends IEntity> type();

    boolean single() default false;
}
