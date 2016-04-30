package com.zhytnik.bank.backend.tool;

import com.zhytnik.bank.backend.domain.IEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Iterators.getOnlyElement;
import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;

public class EntityRelationUtil {

    private EntityRelationUtil() {
    }

    public static <T extends IEntity> List<IEntity> getChildRelationGraph(T entity) {
        return getChildRelationGraph((Object) entity);
    }

    private static List<IEntity> getChildRelationGraph(Object target) {
        final List<IEntity> graph = new ArrayList<>();
        final List<Field> fields = getFields(target.getClass());

        for (Field field : fields) {
            tryFindSingleEntity(graph, target, field);
            tryFindEntitiesCollection(target, graph, field);
        }
        return graph;
    }

    private static void tryFindSingleEntity(List<IEntity> graph, Object target, Field field) {
        if (isNotEmptyEntityField(target, field)) {
            final Object child = getFieldValue(target, field);
            graph.add((IEntity) child);
            graph.addAll(getChildRelationGraph(child));
        }
    }

    private static void tryFindEntitiesCollection(Object target, List<IEntity> graph, Field field) {
        if (isEntityCollection(field, target)) {
            final Collection<IEntity> childEntities = (Collection<IEntity>) getFieldValue(target, field);
            for (IEntity childEntity : childEntities) {
                graph.add(childEntity);
                graph.addAll(getChildRelationGraph(childEntity));
            }
        }
    }

    private static boolean isEntityCollection(Field field, Object target) {
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
}
