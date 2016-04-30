package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.domain.IEntity;
import oracle.sql.STRUCT;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;
import static java.lang.String.format;

public class AggregateUtil {

    private AggregateUtil() {
    }

    public static <T extends IEntity> void fill(T entity, CallableStatement s) {
        int index = 2;

        for (Field field : getFields(entity)) {
            if (isIdField(field) || isReferenceField(field)) continue;

            final Class type = field.getType();

            if (isString(type)) {
                setFieldValue(entity, field, loadString(s, index));
            } else if (isInteger(type)) {
                setFieldValue(entity, field, loadInteger(s, index));
            } else if (isDate(type)) {
                setFieldValue(entity, field, loadDate(s, index));
            } else if (isDouble(type)) {
                setFieldValue(entity, field, loadDecimal(s, index));
            } else if (isEntity(type)) {
                final IEntity entityField = (IEntity) getFieldValue(entity, field);
                entityField.setId(getInteger(loadBigDecimal(s, index)));
            } else {
                throw new RuntimeException(format("There is no rule for field %s", field.getName()));
            }
            index++;
        }
    }

    public static <T extends IEntity> T fill(T entity, STRUCT struct) {
        int index = 0;
        try {
            Object[] attrs = struct.getAttributes();

            for (Field field : getFields(entity)) {
                if (isReferenceField(field)) continue;

                final Class type = field.getType();
                final Object value = attrs[index];

                if (isString(type)) {
                    setFieldValue(entity, field, value);
                } else if (isInteger(type)) {
                    setFieldValue(entity, field, getInteger(value));
                } else if (isDate(type)) {
                    setFieldValue(entity, field, value);
                } else if (isDouble(type)) {
                    setFieldValue(entity, field, value);
                } else if (isDependenceField(field)) {
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

    private static Integer getInteger(Object obj) {
        if (obj == null) return 0;
        final BigDecimal decimal = (BigDecimal) obj;
        return decimal.intValue();
    }
}
