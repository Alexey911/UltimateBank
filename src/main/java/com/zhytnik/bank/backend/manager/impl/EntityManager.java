package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.domain.IEntity;
import com.zhytnik.bank.backend.manager.IEntityManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import java.util.Set;

import static com.zhytnik.bank.backend.manager.impl.ManagerContainer.getManager;
import static com.zhytnik.bank.backend.tool.EntityRelationUtil.getChildRelationGraph;
import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.ScriptUtil.*;
import static com.zhytnik.bank.backend.tool.statement.AggregateUtil.fill;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;
import static com.zhytnik.bank.backend.tool.statement.PrepareUtil.State.*;
import static com.zhytnik.bank.backend.tool.statement.PrepareUtil.prepare;
import static java.lang.String.format;
import static java.util.Collections.reverse;

public class EntityManager<T extends IEntity> implements IEntityManager<T> {

    private static Connection connection;

    static {
        connection = new ConnectionManager().getConnection();
    }

    private Class<T> clazz;
    private Logger logger;

    public EntityManager(Class<T> clazz) {
        this.clazz = clazz;
        logger = Logger.getLogger(this.getClass());
        ManagerContainer.save(clazz, this);
    }

    public T load(Integer id) {
        logger.debug(format("Loading %s[id=%d]", clazz.getSimpleName(), id));

        final T entity = instantiate(clazz, id);
        load(entity, true);
        return entity;
    }

    private void load(T entity, boolean fullLoad) {
        final CallableStatement s = buildStatement(LOAD_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, LOAD, entity);
        execute(s);

        fill(entity, s);
        fillDependencies(entity);
        if (fullLoad) fillReferences(entity);

        close(s);
    }

    private void fillReferences(T entity) {
        for (Field field : getReferenceFields(clazz)) {
            final EntityManager<IEntity> manager = getManager(getFieldReferenceType(field));
            final Set<IEntity> references = manager.findByFieldValue(getEntityName(entity) + ".id", entity.getId());
            fillCollection(field, entity, references);
        }
    }

    @Override
    public Integer save(T entity) {
        logger.debug(format("Saving %s", clazz.getSimpleName()));

        saveRelations(entity);

        final CallableStatement s = buildStatement(SAVE_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, CREATE, entity);
        execute(s);

        final int id = loadInteger(s, 1);
        entity.setId(id);

        close(s);
        return id;
    }

    private void saveRelations(T entity) {
        final List<? extends IEntity> relations = getChildRelationGraph(entity);
        reverse(relations);

        for (IEntity child : relations) {
            final EntityManager<IEntity> m = getManager(child.getClass());
            if (child.isSaved()) {
                m.update(entity);
            } else {
                m.save(child);
            }
        }
    }

    public Set<T> loadAll() {
        logger.debug(format("Loading All %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(LOAD_ALL_FUNCTION_NAME, true, 0);
        registerCollection(s, 1, clazz);
        execute(s);

        final Set<T> entities = extractEntities(s, clazz);
        fillDependencies(entities);

        close(s);
        return entities;
    }

    private void fillDependencies(Set<T> entities) {
        entities.forEach(this::fillDependencies);
    }

    private void fillDependencies(T entity) {
        for (IEntity child : getChildRelationGraph(entity)) {
            getManager(child.getClass()).load(child, false);
        }
    }

    public void update(T entity) {
        logger.debug(format("Updating %s[id=%d]", clazz.getSimpleName(), entity.getId()));

        final CallableStatement s = buildStatement(UPDATE_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, UPDATE, entity);
        execute(s);

        close(s);
    }

    public int getCount() {
        logger.debug(format("Getting count %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(COUNT_FUNCTION_NAME, true, 0);
        registerParameter(s, 1, Integer.class);
        execute(s);

        final int count = loadInteger(s, 1);

        close(s);
        return count;
    }

    public void remove(T entity) {
        remove(entity.getId());
    }

    public void remove(Integer id) {
        logger.debug(format("Removing %s[id=%d]", clazz.getSimpleName(), id));

        final CallableStatement s = buildStatement(REMOVE_PROCEDURE_NAME, false, 1);
        putInteger(s, 1, id);
        execute(s);

        close(s);
    }

    @Override
    public Set<T> findByFieldValue(String field, Object value) {
        return filterByField(loadAll(), field, value);
    }

    @Override
    public void clear() {
        loadAll().forEach(this::remove);
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    private int getFieldsCount() {
        return getSimpleFields(clazz).size();
    }

    private CallableStatement buildStatement(String callName, boolean isFunction, int argsCount) {
        return build(connection, clazz, callName, isFunction, argsCount);
    }
}
