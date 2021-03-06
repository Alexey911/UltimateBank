package com.zhytnik.bank.backend.tool;

import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.backend.types.relation.ManyToOne;
import com.zhytnik.bank.backend.types.relation.OneToMany;
import com.zhytnik.bank.backend.types.relation.OneToOne;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterators.getOnlyElement;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Character.toUpperCase;
import static java.util.Arrays.asList;

public class ReflectionUtil {

    private static final String ID_FIELD = "id";
    private static final String SETTER = "set";
    private static final String GETTER = "get";

    private ReflectionUtil() {
    }

    /*ENTITY INFO*/

    public static boolean isEntity(Class type) {
        return IEntity.class.isAssignableFrom(type);
    }

    public static <T extends IEntity> String getEntityName(T entity) {
        return entity.getClass().getSimpleName().toLowerCase();
    }

    /*INSTANTIATE ENTITY*/

    public static <T extends IEntity> T instantiate(Class<T> clazz) {
        return instantiate(clazz, null);
    }

    public static <T extends IEntity> T instantiate(Class<T> clazz, Integer id) {
        return (T) notSecuredInstantiate(clazz, id, true);
    }

    public static <T extends IEntity> T getInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /*ENTITY ACCESSORS*/

    public static boolean isNullField(Object target, Field field) {
        return getFieldValue(target, field) == null;
    }

    public static void setFieldValue(Object target, Field field, Object value) {
        setFieldValue(target, field.getName(), value);
    }

    public static void setFieldValue(Object target, String field, Object value) {
        final Method setter = findFieldSetter(target.getClass(), field);
        try {
            setter.invoke(target, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object target, Field field) {
        return getFieldValue(target, field.getName());
    }

    public static Object getFieldValue(Object target, String field) {
        final Method method = findMethodByName(target.getClass(), getGetterName(field));

        Object value;
        try {
            value = method.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    /*FIELDS INFO*/

    public static <T extends IEntity> List<Field> getFields(T entity) {
        return getFields(entity.getClass());
    }

    public static List<Field> getSimpleFields(Class clazz) {
        return getFields(clazz).stream().filter(field -> !isOneToManyField(field)).collect(Collectors.toList());
    }

    public static List<Field> getReferenceFields(Class clazz) {
        return getFields(clazz).stream().filter(ReflectionUtil::isOneToManyField).collect(Collectors.toList());
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

    public static boolean isNotEmptyEntityField(Field field, Object target) {
        return isEntity(field.getType()) && getFieldValue(target, field) != null;
    }

    public static boolean isEntityCollection(Field field, Object target) {
        boolean isEntityCollection = false;

        final Object value = getFieldValue(target, field);
        if (value instanceof Collection<?>) {
            final Collection<?> collection = (Collection<?>) value;
            if (!collection.isEmpty()) {
                final Object element = getOnlyElement(collection.iterator());
                if (element instanceof IEntity) {
                    isEntityCollection = true;
                }
            }
        }
        return isEntityCollection;
    }

    public static <T extends IEntity> Set<T> filterByField(Set<T> entities, String field, Object value) {
        final Set<T> filtered = newHashSet();
        for (T entity : entities) {
            final Object fieldValue = getComplexFieldValue(entity, field);
            if (fieldValue != null && fieldValue.equals(value)) {
                filtered.add(entity);
            }
        }
        return filtered;
    }

    public static boolean isFieldReferenceCollection(Field field, Object target) {
        return isEntityCollection(field, target) && isOneToManyField(field);
    }

    public static Class<? extends IEntity> getFieldReferenceType(Field field) {
        for (Annotation a : field.getAnnotations()) {
            if (a instanceof OneToMany) {
                OneToMany reference = (OneToMany) a;
                return reference.type();
            }
        }
        throw new RuntimeException();
    }

    public static void fillCollection(Field field, Object target, Collection<IEntity> values) {
        final Collection<IEntity> collection = getFieldAsCollection(target, field);
        collection.clear();
        collection.addAll(values);
    }

    /*CHECK FIELDS*/

    public static boolean isIdField(Field field) {
        final String name = field.getName();
        return name.equals(ID_FIELD);
    }

    public static boolean isOneToManyField(Field field) {
        return hasAnnotation(field, OneToMany.class);
    }

    public static boolean isManyToOneField(Field field) {
        return hasAnnotation(field, ManyToOne.class);
    }

    public static boolean isOneToOneField(Field field) {
        return hasAnnotation(field, OneToOne.class);
    }

    public static boolean isCollectionField(Field field) {
        return isCollection(field.getType());
    }

    /*CHECK PRIMITIVE TYPES*/

    public static boolean isDouble(Class clazz) {
        return clazz.equals(Double.class);
    }

    public static boolean isBoolean(Class clazz) {
        return clazz.equals(Boolean.class);
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

    private static boolean hasAnnotation(Field field, Class annotation) {
        for (Annotation a : field.getAnnotations()) {
            if (a.annotationType().equals(annotation)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCollection(Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    private static Collection<IEntity> getFieldAsCollection(Object target, Field field) {
        return (Collection<IEntity>) getFieldValue(target, field);
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

    private static Object notSecuredInstantiate(Class<?> clazz, boolean initiateOneToOne) {
        return notSecuredInstantiate(clazz, null, initiateOneToOne);
    }

    private static Object notSecuredInstantiate(Class<?> clazz, Integer id,
                                                boolean initiateOneToOne) {
        try {
            final IEntity entity = (IEntity) clazz.newInstance();
            entity.setId(id);
            initialInnerEntities(entity, initiateOneToOne);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initialInnerEntities(IEntity entity, boolean initiateOneToOne) {
        for (Field field : getFields(entity)) {
            if (isEntity(field.getType())) {
                if (isOneToOneField(field)) {
                    if (!initiateOneToOne) continue;
                    initiateOneToOne = !initiateOneToOne;
                }
                final Object initialVal = notSecuredInstantiate(field.getType(), initiateOneToOne);
                setFieldValue(entity, field.getName(), initialVal);
            }
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

    private static Object getComplexFieldValue(Object target, String field) {
        for (String aField : field.split("\\.")) {
            if(target != null) {
                target = getFieldValue(target, aField);
            }
        }
        return target;
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

    private static boolean isBasicClass(Class clazz) {
        return clazz == null || clazz.equals(Object.class);
    }

    private static String getSetterName(String field) {
        return SETTER + toUpperCase(field.charAt(0)) + field.substring(1);
    }

    private static String getGetterName(String field) {
        return GETTER + toUpperCase(field.charAt(0)) + field.substring(1);
    }
}
