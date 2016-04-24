package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.domain.Depends;
import com.zhytnik.bank.backend.domain.IEntity;
import oracle.sql.STRUCT;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;

public class AggregateUtil {

    private AggregateUtil() {
    }

    public static <T extends IEntity> void fill(T entity, CallableStatement s) {
        int index = 2;

        for (Field field : getFields(entity.getClass())) {
            if (isIdField(field)) continue;

            final Class typeClass = field.getType();
            final String attr = field.getName();

            if (isString(typeClass)) {
                setFieldValue(entity, attr, loadString(s, index));
            } else if (isInteger(typeClass)) {
                setFieldValue(entity, attr, loadInteger(s, index));
            } else if (isDate(typeClass)) {
                setFieldValue(entity, attr, loadDate(s, index));
            } else if (isDouble(typeClass)) {
                setFieldValue(entity, attr, loadDecimal(s, index));
            } else if (isEntity(typeClass)) {
                final IEntity entityField = (IEntity) getFieldValue(entity, field);
                entityField.setId(loadBigDecimal(s, index).intValue());
            } else {
                throw new RuntimeException();
            }
            index++;
        }
    }

    public static <T extends IEntity> T fill(T entity, STRUCT struct) {
        int index = 0;
        try {
            Object[] attrs = struct.getAttributes();

            for (Field field : getFields(entity.getClass())) {

                final Class typeClass = field.getType();
                final String attr = field.getName();
                final Object value = attrs[index];

                if (isString(typeClass)) {
                    setFieldValue(entity, attr, value);
                } else if (isInteger(typeClass)) {
                    setFieldValue(entity, attr, getInteger(value));
                } else if (isDate(typeClass)) {
                    setFieldValue(entity, attr, value);
                } else if (isDouble(typeClass)) {
                    setFieldValue(entity, attr, value);
                } else if (hasAnnotation(field, Depends.class)) {
                    final IEntity entityField = (IEntity) getFieldValue(entity, field);
                    entityField.setId(getInteger(value));
                } else {
                    throw new RuntimeException();
                }
                index++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    private static int getInteger(Object obj) {
        final BigDecimal decimal = (BigDecimal) obj;
        return decimal.intValue();
    }
}
