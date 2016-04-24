package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.domain.IEntity;
import com.zhytnik.bank.backend.manager.IEntityManager;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Set;

import static com.zhytnik.bank.tool.AggregateUtil.fill;
import static com.zhytnik.bank.tool.CallableStatementUtil.*;
import static com.zhytnik.bank.tool.PrepareUtil.State.*;
import static com.zhytnik.bank.tool.PrepareUtil.prepare;
import static com.zhytnik.bank.tool.ReflectionUtil.getFields;
import static com.zhytnik.bank.tool.ReflectionUtil.instantiate;
import static com.zhytnik.bank.tool.ScriptUtil.*;
import static java.lang.String.format;

public abstract class EntityManager<T extends IEntity> implements IEntityManager<T> {

    private Class<T> clazz;
    private Connection connection;
    private Logger logger;

    public EntityManager(Class<T> clazz) {
        this.clazz = clazz;
        connection = new ConnectionManager().getConnection();
        logger = Logger.getLogger(this.getClass());
    }

    public T load(Integer id) {
        logger.debug(format("Loading %s[id=%d]", clazz.getSimpleName(), id));

        final T entity = instantiate(clazz, id);

        final CallableStatement s = buildStatement(LOAD_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, LOAD, entity);

        execute(s);
        fill(entity, s);
        close(s);
        return entity;
    }

    @Override
    public Integer save(T entity) {
        logger.debug(format("Saving %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(SAVE_PROCEDURE_NAME, false, getFieldsCount());
        prepare(s, CREATE, entity);

        execute(s);
        final int id = loadInteger(s, 1);
        entity.setId(id);
        close(s);
        return id;
    }

    public Set<T> loadAll() {
        logger.debug(format("Loading All %s", clazz.getSimpleName()));

        final CallableStatement s = buildStatement(LOAD_ALL_FUNCTION_NAME, true, 0);
        registerCollection(s, 1, clazz);

        execute(s);
        final Set<T> entities = extractEntities(s, clazz);
        close(s);
        return entities;
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
        return getFields(clazz).size();
    }

    private CallableStatement buildStatement(String callName, boolean isFunction, int argsCount) {
        return build(connection, clazz, callName, isFunction, argsCount);
    }
}
