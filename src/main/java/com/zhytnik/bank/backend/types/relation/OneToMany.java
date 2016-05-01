package com.zhytnik.bank.backend.types.relation;

import com.zhytnik.bank.backend.types.IEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface OneToMany {
    Class<? extends IEntity> type();

    boolean single() default false;
}
