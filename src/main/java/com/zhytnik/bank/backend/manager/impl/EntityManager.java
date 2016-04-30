package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.domain.IEntity;
import com.zhytnik.bank.backend.manager.IEntityManager;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
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

    private static Connection connection;
    private static ManagerContainer container;

    static {
        connection = new ConnectionManager().getConnection();
        container = new ManagerContainer();
    }

    private Class<T> clazz;
    private Logger logger;

    public EntityManager(Class<T> clazz) {
        this.clazz = clazz;
        logger = Logger.getLogger(this.getClass());
        container.save(clazz, this);
    }

    public T load(Integer id) {
        logger.debug(format("Loading %s[id=%d]", clazz.getSimpleName(), id));

        final T entity = instantiate(clazz, id);
        load(entity);
        return entity;
    }

    private void load(T entity) {
        final CallableStatement s = buildStatement(LOAD_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, LOAD, entity);
        execute(s);

        fill(entity, s);
        fillRelations(entity);

        close(s);
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
        final List<IEntity> relations = getChildRelationGraph(entity);
        reverse(relations);

        for (IEntity child : relations) {
            container.get(child.getClass()).save(child);
        }
    }

    public Set<T> loadAll() {
        logger.debug(format("Loading All %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(LOAD_ALL_FUNCTION_NAME, true, 0);
        registerCollection(s, 1, clazz);
        execute(s);

        final Set<T> entities = extractEntities(s, clazz);
        fillRelations(entities);

        close(s);
        return entities;
    }

    private void fillRelations(Set<T> entities) {
        entities.forEach(this::fillRelations);
    }

    private void fillRelations(T entity) {
        for (IEntity child : getChildRelationGraph(entity)) {
            container.get(child.getClass()).load(child);
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
