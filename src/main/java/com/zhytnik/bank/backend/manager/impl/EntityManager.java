package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.tool.ReflectionUtil;
import com.zhytnik.bank.backend.types.IEntity;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.util.List;
import java.util.Set;

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

    protected Class<T> clazz;

    protected ManagerFactory managerFactory;

    protected ConnectionManager connectionManager;

    protected Logger logger = Logger.getLogger(this.getClass());

    public EntityManager(Class<T> clazz) {
        this.clazz = clazz;
    }

    public EntityManager() {
    }

    public T load(Integer id) {
        final T entity = ReflectionUtil.instantiate(clazz, id);
        load(entity, true);
        return entity;
    }

    @Override
    public T instantiate() {
        return getInstance(clazz);
    }

    protected void load(T entity, boolean fullLoad) {
        logger.debug(format("Loading %s[id=%d]", clazz.getSimpleName(), entity.getId()));

        final CallableStatement s = buildStatement(LOAD_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, LOAD, entity);
        execute(s);

        fill(entity, s);
        connectionManager.close(s);

        fillDependencies(entity);
        if (fullLoad) fillReferences(entity);
    }

    protected void fillReferences(T entity) {
        for (Field field : getReferenceFields(clazz)) {
            final IEntityManager<IEntity> manager = managerFactory.getEntityManager(getFieldReferenceType(field));
            final Set<IEntity> references = manager.findByFieldValue(getEntityName(entity) + ".id", entity.getId());
            fillCollection(field, entity, references);
        }
    }

    @Override
    public void initialize(T entity) {
        fillReferences(entity);
    }

    @Override
    public Integer save(T entity) {
        return save(entity, true);
    }

    public Integer save(T entity, boolean fullSave) {
        logger.debug(format("Saving %s", clazz.getSimpleName()));

        if (fullSave) saveDependencies(entity);

        final CallableStatement s = buildStatement(SAVE_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, CREATE, entity);
        execute(s);

        final int id = loadInteger(s, 1);
        entity.setId(id);

        connectionManager.close(s);
        return id;
    }

    private void saveDependencies(T entity) {
        final List<? extends IEntity> relations = getChildRelationGraph(entity);
        reverse(relations);

        for (IEntity child : relations) {
            final EntityManager<IEntity> m = managerFactory.getEntityManager(child.getClass());
            if (child.isSaved()) {
                m.update(child);
            } else {
                m.save(child, false);
            }
        }
    }

    public Set<T> loadAll() {
        logger.debug(format("Loading All %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(LOAD_ALL_FUNCTION_NAME, true, 0);
        registerCollection(s, 1, clazz);
        execute(s);

        final Set<T> entities = extractEntities(s, clazz);
        connectionManager.close(s);

        fillDependencies(entities);
        return entities;
    }

    private void fillDependencies(Set<T> entities) {
        entities.forEach(this::fillDependencies);
    }

    private void fillDependencies(T entity) {
        for (IEntity child : getChildRelationGraph(entity)) {
            if (child.isSaved()) {
                managerFactory.getEntityManager(child.getClass()).load(child, false);
            }
        }
    }

    public void update(T entity) {
        logger.debug(format("Updating %s[id=%d]", clazz.getSimpleName(), entity.getId()));

        final CallableStatement s = buildStatement(UPDATE_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, UPDATE, entity);
        execute(s);

        connectionManager.close(s);
    }

    public int getCount() {
        logger.debug(format("Getting count %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(COUNT_FUNCTION_NAME, true, 0);
        registerParameter(s, 1, Integer.class);
        execute(s);

        final int count = loadInteger(s, 1);

        connectionManager.close(s);
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

        connectionManager.close(s);
    }

    @Override
    public Set<T> findByFieldValue(String field, Object value) {
        return filterByField(loadAll(), field, value);
    }

    @Override
    public void clear() {
        logger.debug(format("Clearing %s type", clazz.getSimpleName()));
        final CallableStatement s = buildStatement(CLEAR_PROCEDURE_NAME, false, 0);
        execute(s);
        connectionManager.close(s);
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    private int getFieldsCount() {
        return getSimpleFields(clazz).size();
    }

    private CallableStatement buildStatement(String callName, boolean isFunction, int argsCount) {
        return build(connectionManager.getConnection(), clazz, callName, isFunction, argsCount);
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void setManagerFactory(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }
}
