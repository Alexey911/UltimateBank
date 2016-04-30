package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.domain.IEntity;

import java.lang.reflect.Field;
import java.sql.CallableStatement;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;
import static java.lang.String.format;

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

        for (Field field : getFields(entity)) {
            if (isIdField(field) || isReferenceField(field)) continue;
            registerParameter(s, index++, field.getType());
        }
    }

    private static <T extends IEntity> void prepareSaveStatement(CallableStatement s, T entity) {
        int index = 1;
        registerParameter(s, index++, Integer.class);

        for (Field field : getFields(entity)) {
            if (isIdField(field) || isReferenceField(field)) continue;

            final Class typeClass = field.getType();

            if (isString(typeClass)) {
                putString(s, index, getFieldValue(entity, field));
            } else if (isInteger(typeClass)) {
                putInteger(s, index, getFieldValue(entity, field));
            } else if (isDate(typeClass)) {
                putDate(s, index, getFieldValue(entity, field));
            } else if (isDouble(typeClass)) {
                putDecimal(s, index, getFieldValue(entity, field));
            } else if (isDependenceField(field)) {
                putForeignKey(s, entity, index, field);
            } else {
                throw new RuntimeException(format("Unknown Entity Field %s", field.getName()));
            }
            index++;
        }
    }

    private static <T extends IEntity> void putForeignKey(CallableStatement s, T entity, int index, Field field) {
        final IEntity dependEntity = (IEntity) getFieldValue(entity, field);
        putInteger(s, index, dependEntity.getId());
    }

    private static <T extends IEntity> void prepareUpdateStatement(CallableStatement s, T entity) {
        int index = 1;

        for (Field field : getFields(entity)) {
            if (isReferenceField(field)) continue;

            final Class type = field.getType();

            if (isString(type)) {
                putString(s, index, getFieldValue(entity, field));
            } else if (isInteger(type)) {
                putInteger(s, index, getFieldValue(entity, field));
            } else if (isDate(type)) {
                putDate(s, index, getFieldValue(entity, field));
            } else if (isDouble(type)) {
                putDecimal(s, index, getFieldValue(entity, field));
            } else if (isDependenceField(field)) {
                putForeignKey(s, entity, index, field);
            } else {
                throw new RuntimeException(format("Unknown Entity Field %s", field.getName()));
            }
            index++;
        }
    }
}
