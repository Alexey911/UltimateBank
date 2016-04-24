package com.zhytnik.bank.tool;

import com.zhytnik.bank.backend.domain.IEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Character.toUpperCase;
import static java.util.Arrays.asList;

public class ReflectionUtil {

    private static final String SETTER = "set";
    private static final String GETTER = "get";

    private ReflectionUtil() {

    }

    public static <T extends IEntity> T instantiate(Class<T> clazz) {
        return instantiate(clazz, null);
    }

    public static <T extends IEntity> T instantiate(Class<T> clazz, Integer id) {
        try {
            final T entity = clazz.newInstance();
            entity.setId(id);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFieldValue(Object object, String field, Object value) {
        final Method setter = findFieldSetter(object.getClass(), field);
        try {
            setter.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method findFieldSetter(Class clazz, String field) {
        final String setter = getSetterName(field);
        final Class type = getFieldType(clazz, field);

        Method method;
        try {
            method = clazz.getMethod(setter, type);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return method;
    }

    private static String getSetterName(String field) {
        return SETTER + toUpperCase(field.charAt(0)) + field.substring(1);
    }

    private static String getGetterName(String field) {
        return GETTER + toUpperCase(field.charAt(0)) + field.substring(1);
    }

    private static Class getFieldType(Class clazz, String name) {
        final List<Field> fields = getFields(clazz);
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field.getType();
            }
        }
        return null;
    }

    public static List<Field> getFields(Class clazz) {
        final List<Field> fields = new ArrayList<>();

        final Class superClass = clazz.getSuperclass();
        if (!isBasicClass(superClass)) {
            fields.addAll(getFields(superClass));
        }

        fields.addAll(asList(clazz.getDeclaredFields()));
        return fields;
    }

    private static boolean isBasicClass(Class clazz) {
        return clazz == null || clazz.equals(Object.class);
    }

    public static Object getFieldValue(Object object, Field field) {
        return getFieldValue(object, field.getName());
    }

    public static Object getFieldValue(Object object, String field) {
        final Method method = findMethodByName(object.getClass(), getGetterName(field));

        Object value;
        try {
            value = method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    private static Method findMethodByName(Class clazz, String name) {
        Method method;
        try {
            method = clazz.getMethod(name);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return method;
    }

    public static boolean isIdField(Field field) {
        final String name = field.getName();
        return name.equals("id");
    }

    public static boolean isDouble(Class clazz) {
        return clazz.equals(Double.class);
    }

    public static boolean isDate(Class clazz) {
        return clazz.equals(Date.class);
    }

    public static boolean isInteger(Class clazz) {
        return clazz.equals(Integer.class);
    }

    public static boolean isString(Class clazz) {
        return clazz.equals(String.class);
    }
}
