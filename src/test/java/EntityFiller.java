import com.zhytnik.bank.backend.types.IEntity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;

class EntityFiller {

    private static Random random = new Random();

    private EntityFiller() {
    }

    public static <T extends IEntity> T create(Class<T> clazz) {
        return (T) createAndFill(clazz, true);
    }

    private static Object createAndFill(Class clazz, boolean initiateOneToOne) {
        final Object object = instantiate(clazz);

        for (Field field : getFields(clazz)) {
            if (isIdField(field) || isCollectionField(field)) continue;
            if (isOneToOneField(field)) {
                if (!initiateOneToOne) continue;
                initiateOneToOne = !initiateOneToOne;
            }
            Object value = getValueForField(field, initiateOneToOne);
            setFieldValue(object, field, value);
        }
        return object;
    }

    private static Object instantiate(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setFieldValue(Object target, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getValueForField(Field field, boolean initiateOneToOne) {
        final Class<?> type = field.getType();

        if (!isIdField(field) && isInteger(type)) {
            return random.nextInt();
        } else if (isDouble(type)) {
            return random.nextDouble();
        } else if (isString(type)) {
            return UUID.randomUUID().toString();
        } else if (isDate(type)) {
            return new Date(random.nextInt());
        } else if (isBoolean(type)) {
            return random.nextBoolean();
        }
        return createAndFill(type, initiateOneToOne);
    }
}
