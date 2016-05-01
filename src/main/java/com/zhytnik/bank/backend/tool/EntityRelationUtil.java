package com.zhytnik.bank.backend.tool;

import com.zhytnik.bank.backend.types.IEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        if (isNotEmptyEntityField(field, target)) {
            final Object child = getFieldValue(target, field);
            graph.add((IEntity) child);
            graph.addAll(getChildRelationGraph(child));
        }
    }

    private static void tryFindEntitiesCollection(Object target, List<IEntity> graph, Field field) {
        if (isEntityCollection(field, target) && !isFieldReferenceCollection(field, target)) {
            final Collection<IEntity> childEntities = (Collection<IEntity>) getFieldValue(target, field);
            for (IEntity childEntity : childEntities) {
                graph.add(childEntity);
                graph.addAll(getChildRelationGraph(childEntity));
            }
        }
    }
}
