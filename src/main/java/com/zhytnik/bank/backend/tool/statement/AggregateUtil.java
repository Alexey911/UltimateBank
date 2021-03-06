package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.types.IEntity;
import oracle.sql.STRUCT;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;

public class AggregateUtil {

    private AggregateUtil() {
    }

    public static <T extends IEntity> void fill(T entity, CallableStatement s) {
        int index = 2;

        for (Field field : getFields(entity)) {
            if (isIdField(field) || isOneToManyField(field)) continue;

            final Class type = field.getType();

            if (isString(type)) {
                setFieldValue(entity, field, loadString(s, index));
            } else if (isInteger(type)) {
                setFieldValue(entity, field, loadInteger(s, index));
            } else if (isDate(type)) {
                setFieldValue(entity, field, loadDate(s, index));
            } else if (isDouble(type)) {
                setFieldValue(entity, field, loadDouble(s, index));
            } else if (isBoolean(type)) {
                setFieldValue(entity, field, loadBoolean(s, index));
            } else if (isEntity(type)) {
                setEntityField(s, index, entity, field);
            } else {
                throw new RuntimeException(format("There is no rule for field %s", field.getName()));
            }
            index++;
        }
    }

    private static void setEntityField(CallableStatement s, int index, Object target, Field field) {
        final IEntity entity = (IEntity) getFieldValue(target, field);

        if (entity == null) return;

        final Integer id = getInteger(loadBigDecimal(s, index));
        if (id != null) {
            entity.setId(id);
        } else {
            setFieldValue(target, field, null);
        }
    }

    public static <T extends IEntity> T fill(T entity, STRUCT struct) {
        int index = 0;
        try {
            Object[] attrs = struct.getAttributes();

            for (Field field : getFields(entity)) {
                if (isOneToManyField(field)) continue;

                final Class type = field.getType();
                final Object value = attrs[index];

                if (isString(type)) {
                    setFieldValue(entity, field, value);
                } else if (isInteger(type)) {
                    setFieldValue(entity, field, getInteger(value));
                } else if (isDate(type)) {
                    setFieldValue(entity, field, value);
                } else if (isDouble(type)) {
                    setFieldValue(entity, field, getDouble(value));
                } else if (isBoolean(type)) {
                    setFieldValue(entity, field, getBoolean(value));
                } else if (isManyToOneField(field) || isOneToOneField(field)) {
                    setEntityField(entity, field, value);
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

    private static void setEntityField(Object target, Field field, Object value) {
        final IEntity entity = (IEntity) getFieldValue(target, field);
        final Integer id = getInteger(value);

        if (id != null) {
            entity.setId(id);
        } else {
            setFieldValue(target, field, null);
        }
    }

    private static Boolean getBoolean(Object obj) {
        if (obj == null) return null;
        final Double value = getDouble(obj);
        return (value > 0) ? TRUE : FALSE;
    }

    private static Double getDouble(Object obj) {
        if (obj == null) return 0d;
        final BigDecimal decimal = (BigDecimal) obj;
        return decimal.doubleValue();
    }

    private static Integer getInteger(Object obj) {
        if (obj == null) return null;
        final BigDecimal decimal = (BigDecimal) obj;
        return decimal.intValue();
    }
}
