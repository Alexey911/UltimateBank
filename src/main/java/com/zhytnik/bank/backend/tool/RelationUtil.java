package com.zhytnik.bank.backend.tool;

import com.zhytnik.bank.backend.domain.IEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Iterators.getOnlyElement;
import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;

public class RelationUtil {

    private RelationUtil() {
    }

    public static <T extends IEntity> List<IEntity> getChildRelationGraph(T entity) {
        return getChildRelationGraph((Object) entity);
    }

    private static List<IEntity> getChildRelationGraph(Object entity) {
        final List<IEntity> graph = new ArrayList<>();
        final List<Field> fields = getFields(entity.getClass());

        for (Field field : fields) {
            tryFindSingleEntity(graph, entity, field);
            tryFindEntitiesCollection(entity, graph, field);
        }
        return graph;
    }

    private static void tryFindSingleEntity(List<IEntity> graph, Object entity, Field field) {
        if (isNotEmptyField(entity, field)) {
            final Object childEntity = getFieldValue(entity, field);
            graph.add((IEntity) childEntity);
            graph.addAll(getChildRelationGraph(childEntity));
        }
    }

    private static void tryFindEntitiesCollection(Object entity, List<IEntity> graph, Field field) {
        if (isEntityCollection(entity, field)) {
            final Collection<IEntity> childEntities = (Collection<IEntity>) getFieldValue(entity, field);
            for (IEntity childEntity : childEntities) {
                graph.add(childEntity);
                graph.addAll(getChildRelationGraph(childEntity));
            }
        }
    }

    private static boolean isEntityCollection(Object entity, Field field) {
        boolean isEntityCollection = false;

        final Object value = getFieldValue(entity, field);
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
