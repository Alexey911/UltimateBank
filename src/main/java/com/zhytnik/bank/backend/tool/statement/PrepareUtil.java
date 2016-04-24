package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.domain.Depends;
import com.zhytnik.bank.backend.domain.IEntity;

import java.lang.reflect.Field;
import java.sql.CallableStatement;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;

public class PrepareUtil {

    public enum State {
        CREATE, LOAD, UPDATE
    }

    private PrepareUtil() {
    }

    public static <T extends IEntity> void prepare(CallableStatement s, State state, T entity) {
        switch (state) {
            case LOAD:
                prepareLoadStatement(s, entity);
                break;
            case CREATE:
                prepareSaveStatement(s, entity);
                break;
            case UPDATE:
                prepareUpdateStatement(s, entity);
                break;
        }
    }

    private static <T extends IEntity> void prepareLoadStatement(CallableStatement s, T entity) {
        int index = 1;
        putInteger(s, index++, entity.getId());

        for (Field field : getFields(entity.getClass())) {
            if (isIdField(field)) continue;
            registerParameter(s, index++, field.getType());
        }
    }

    private static <T extends IEntity> void prepareSaveStatement(CallableStatement s, T entity) {
        int index = 1;
        registerParameter(s, index++, Integer.class);

        for (Field field : getFields(entity.getClass())) {
            if (isIdField(field)) continue;

            final Class typeClass = field.getType();

            if (isString(typeClass)) {
                putString(s, index, getFieldValue(entity, field));
            } else if (isInteger(typeClass)) {
                putInteger(s, index, getFieldValue(entity, field));
            } else if (isDate(typeClass)) {
                putDate(s, index, getFieldValue(entity, field));
            } else if (isDouble(typeClass)) {
                putDecimal(s, index, getFieldValue(entity, field));
            } else if (hasDependent(field)) {
                putForeignKey(s, entity, index, field);
            } else {
                throw new RuntimeException();
            }
            index++;
        }
    }

    private static boolean hasDependent(Field field) {
        return hasAnnotation(field, Depends.class);
    }

    private static <T extends IEntity> void putForeignKey(CallableStatement s, T entity, int index, Field field) {
        final IEntity dependEntity = (IEntity) getFieldValue(entity, field);
        putInteger(s, index, dependEntity.getId());
    }

    private static <T extends IEntity> void prepareUpdateStatement(CallableStatement s, T entity) {
        int index = 1;

        for (Field field : getFields(entity.getClass())) {
            final Class typeClass = field.getType();

            if (isString(typeClass)) {
                putString(s, index, getFieldValue(entity, field));
            } else if (isInteger(typeClass)) {
                putInteger(s, index, getFieldValue(entity, field));
            } else if (isDate(typeClass)) {
                putDate(s, index, getFieldValue(entity, field));
            } else if (isDouble(typeClass)) {
                putDecimal(s, index, getFieldValue(entity, field));
            } else if (isEntity(typeClass)) {
                putForeignKey(s, entity, index, field);
            } else {
                throw new RuntimeException();
            }
            index++;
        }
    }
}
